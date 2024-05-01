package io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer.action

import io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer.FileMetadataWriter
import io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer.metadataOrNullWithoutThrows
import io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer.relativeToOrNull
import io.github.andrewk2112.utility.common.utility.Result
import io.github.andrewk2112.utility.common.utility.Result.Companion.getOrElse
import okio.*

/**
 * Attempts to copy all files from a [sourceBundle] into a [deploymentDestination] with the following strategy:
 * 1. If some file from the [sourceBundle] is unmet at the same path in the [deploymentDestination]
 *    then it's just copied with the same last modification time metadata.
 * 2. If some file from the [sourceBundle] is already present at the same path in the [deploymentDestination]
 *    then no actions are taken
 *    if both source and destination files have equal last modification time and size metadata.
 *    Otherwise, the execution aborts with the corresponding error.
 * 3. The execution stops with the corresponding error immediately in the case of any I/O or other types of issues.
 */
internal class PatchingDeployAction(
    private val fileSystem: FileSystem,
    private val fileMetadataWriter: FileMetadataWriter,
    private val sourceBundle: Path,
    private val deploymentDestination: Path,
    private val onFileCopied: ((Path) -> Unit)? = null
) {

    // Public.

    fun execute(): ResultType = copyDirectoryFilesRecursively(sourceBundle)



    // Private.

    /**
     * Iterates through all files inside a [directoryPath]
     * and copies all of them into the [deploymentDestination] with a fail-first strategy.
     */
    private fun copyDirectoryFilesRecursively(directoryPath: Path): ResultType {
        val directoryFiles = fileSystem.listOrNull(directoryPath)
                          ?: return failure(CouldNotReadDirectoryFilesException(directoryPath))
        createMirroredDirectoryInDeploymentDestination(directoryPath).getFailureOrNull()?.let { return it }
        if (directoryFiles.isNotEmpty()) {
            directoryFiles.forEach {
                copyDirectoryFileOrDirectory(it).getFailureOrNull()?.let { failure -> return failure }
            }
        }
        return success
    }

    /**
     * Creates a directory (makes sure this directory exists) inside the [deploymentDestination]
     * with a path similar to a [sourceDirectoryPath] relatively to the [sourceBundle] path.
     */
    private fun createMirroredDirectoryInDeploymentDestination(sourceDirectoryPath: Path): ResultType {
        val destinationDirectory = constructMirroredPathInDeploymentDestination(sourceDirectoryPath)
                                   .getOrElse { return failure(it) }
        return try {
            if (!fileSystem.exists(destinationDirectory)) {
                fileSystem.createDirectory(destinationDirectory)
            }
            success
        } catch (exception: IOException) {
            failure(CouldNotCreateDirectoryException(destinationDirectory, exception))
        }
    }

    /**
     * Checks the type of [filePath] and copies it either as a directory or as a file.
     */
    private fun copyDirectoryFileOrDirectory(filePath: Path): ResultType {
        val fileMetadata = fileSystem.metadataOrNullWithoutThrows(filePath)
                        ?: return failure(CouldNotReadFileMetadataException(filePath))
        return when {
            fileMetadata.isDirectory   -> copyDirectoryFilesRecursively(filePath)
            fileMetadata.isRegularFile -> copyFileToDeploymentDestinationIfNeeded(filePath, fileMetadata)
            else                       -> failure(UnknownFileTypeOrMalformedMetadataException(filePath))
        }
    }

    /**
     * Analyzes the file at [filePath] and copies it if is not present in the [deploymentDestination] yet.
     * Does nothing if the same file having the same [fileMetadata] is already present in the [deploymentDestination].
     * Fails otherwise.
     */
    private fun copyFileToDeploymentDestinationIfNeeded(filePath: Path, fileMetadata: FileMetadata): ResultType {
        val destinationFile = constructMirroredPathInDeploymentDestination(filePath).getOrElse { return failure(it) }
        val destinationFileMetadata = fileSystem.metadataOrNullWithoutThrows(destinationFile)
        return when {
            destinationFileMetadata == null -> // no destination file exists
                copyFile(filePath, fileMetadata, destinationFile).also { onFileCopied?.invoke(destinationFile) }
            destinationFileMetadata.isDirectory ->
                failure(CanNotCopyRegularFileAsDirectoryException(filePath, destinationFile))
            destinationFileMetadata.isRegularFile ->
                checkDestinationFileHasRequiredMetadata(destinationFile, destinationFileMetadata, fileMetadata)
            else -> failure(UnknownFileTypeOrMalformedMetadataException(destinationFile))
        }
    }

    /**
     * Constructs a [Path] in the [deploymentDestination] having the same relative [Path]
     * as a [sourceBundlePath] to the [sourceBundle].
     */
    private fun constructMirroredPathInDeploymentDestination(
        sourceBundlePath: Path
    ): Result<Path, CouldNotConstructDestinationPathException> =
        sourceBundlePath.relativeToOrNull(sourceBundle)?.let { Result.Success(deploymentDestination.div(it)) }
            ?: Result.Failure(CouldNotConstructDestinationPathException(sourceBundlePath))

    /**
     * Also copies last modified time [FileMetadata].
     */
    private fun copyFile(sourcePath: Path, sourceFileMetadata: FileMetadata, destinationPath: Path): ResultType {
        try {
            fileSystem.copy(sourcePath, destinationPath)
        } catch (exception: IOException) {
            return failure(CouldNotCopyFileException(sourcePath, destinationPath, exception))
        }
        val sourceFileLastModifiedAtMillis = sourceFileMetadata.lastModifiedAtMillis
            ?: return failure(CouldNotReadLastModifiedTimeException(sourcePath))
        return if (fileMetadataWriter.setLastModifiedTime(destinationPath.toString(), sourceFileLastModifiedAtMillis)) {
            success
        } else {
            failure(CouldNotSetLastModifiedTimeException(destinationPath))
        }
    }

    /**
     * Checks if [destinationFileMetadata] and [expectedMetadata]
     * have the same [FileMetadata.lastModifiedAtMillis] and [FileMetadata.size] values.
     */
    private fun checkDestinationFileHasRequiredMetadata(
        destinationFile: Path,
        destinationFileMetadata: FileMetadata,
        expectedMetadata: FileMetadata
    ): ResultType =
        if (
            destinationFileMetadata.lastModifiedAtMillis == expectedMetadata.lastModifiedAtMillis &&
            destinationFileMetadata.size                 == expectedMetadata.size
        ) {
            success
        } else {
            failure(
                DestinationFileMustBeUpdatedException(destinationFile, destinationFileMetadata, expectedMetadata)
            )
        }

    private fun failure(exception: PatchingDeployActionException): Result.Failure<Unit, PatchingDeployActionException> =
        Result.Failure(exception)

    private val success = Result.Success<Unit, PatchingDeployActionException>(Unit)

}

private typealias ResultType = Result<Unit, PatchingDeployActionException>
