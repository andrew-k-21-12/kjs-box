package io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer.action

import okio.FileMetadata
import okio.Path



// Basic type.

/**
 * Base class for all possible reasons why [PatchingDeployAction] can fail.
 */
internal sealed class PatchingDeployActionException : Exception {
    constructor() : super()
    constructor(cause: Throwable?) : super(cause)
}



// Possible variants.

/**
 * When a directory is present at a regular file's copy destination.
 */
internal class CanNotCopyRegularFileAsDirectoryException(
    val sourceFile: Path,
    val destinationDirectory: Path
) : PatchingDeployActionException()

/**
 * When a mirrored [Path] inside a destination directory could not be prepared according to a source [Path].
 */
internal class CouldNotConstructDestinationPathException(val sourcePath: Path) : PatchingDeployActionException()

internal class CouldNotCopyFileException(
    val sourcePath: Path,
    val destinationPath: Path,
    cause: Throwable
) : PatchingDeployActionException(cause)

internal class CouldNotCreateDirectoryException(
    val directoryPath: Path,
    cause: Throwable
) : PatchingDeployActionException(cause)

internal class CouldNotReadDirectoryFilesException(val directoryPath: Path) : PatchingDeployActionException()

internal class CouldNotReadFileMetadataException(val filePath: Path) : PatchingDeployActionException()

internal class CouldNotReadLastModifiedTimeException(val filePath: Path) : PatchingDeployActionException()

internal class CouldNotSetLastModifiedTimeException(val destinationFile: Path) : PatchingDeployActionException()

/**
 * When a destination file to copy a source file to is already present but has another metadata.
 */
internal class DestinationFileMustBeUpdatedException(
    val destinationFile: Path,
    val destinationFileMetadata: FileMetadata,
    val expectedMetadata: FileMetadata
) : PatchingDeployActionException()

internal class UnknownFileTypeOrMalformedMetadataException(val filePath: Path) : PatchingDeployActionException()
