package io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer

import io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer.action.*
import io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer.action.CanNotCopyRegularFileAsDirectoryException
import io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer.action.CouldNotConstructDestinationPathException
import io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer.action.CouldNotCopyFileException
import io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer.action.CouldNotCreateDirectoryException
import io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer.action.CouldNotReadDirectoryFilesException
import io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer.action.CouldNotReadFileMetadataException
import io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer.action.CouldNotReadLastModifiedTimeException
import io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer.action.CouldNotSetLastModifiedTimeException
import io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer.action.DestinationFileMustBeUpdatedException
import io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer.action.PatchingDeployActionException
import io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer.action.UnknownFileTypeOrMalformedMetadataException
import io.github.andrewk2112.utility.common.utility.Result
import okio.Path

/**
 * Prints an intermediate progress and result of [PatchingDeployAction]'s execution.
 */
internal class ProgressAndResultPrinter {

    // Public.

    fun printProgress(copiedFile: Path) {
        println("Copied: \"$copiedFile\".")
    }

    fun printResult(result: Result<Unit, PatchingDeployActionException>) {
        when (result) {
            is Result.Success -> printSuccess()
            is Result.Failure -> printFailure(result.exception)
        }
    }



    // Private.

    private fun printSuccess() {
        println("Patching deployment has succeeded! \uD83C\uDF89")
        println("Now just swap \"index.html\" with a new one to turn the updated version on.")
    }

    private fun printFailure(exception: PatchingDeployActionException) {
        println("Patching deployment has failed! ❌")
        println(createErrorMessageForException(exception))
        println("Most likely it is needed to perform a full deployment:")
        println("deploy the entire bundle with an increased version and then swap \"index.html\" to apply the release.")
    }

    private fun createErrorMessageForException(exception: PatchingDeployActionException): String =
        when (exception) {
            is CanNotCopyRegularFileAsDirectoryException -> """
                Can not copy file "${exception.sourceFile}"
                as it's destination "${exception.destinationDirectory}" is a folder.
            """.trimIndent()
            is CouldNotConstructDestinationPathException -> """
                Could not construct a path in the deployment destination folder
                to copy "${exception.sourcePath}".
            """.trimIndent()
            is CouldNotCopyFileException -> """
                Could not copy file "${exception.sourcePath}"
                into "${exception.destinationPath}"
                because: "${exception.cause}".
            """.trimIndent()
            is CouldNotCreateDirectoryException -> """
                Could not create a directory "${exception.directoryPath}"
                because: "${exception.cause}".
            """.trimIndent()
            is CouldNotReadDirectoryFilesException -> """
                Could not read a list of files
                inside "${exception.directoryPath}".
            """.trimIndent()
            is CouldNotReadFileMetadataException -> """
                Could not read file metadata
                of "${exception.filePath}".
            """.trimIndent()
            is CouldNotReadLastModifiedTimeException -> """
                Could not read last modified time
                of "${exception.filePath}".
            """.trimIndent()
            is CouldNotSetLastModifiedTimeException -> """
                Could not set last modified time file metadata
                for "${exception.destinationFile}".
            """.trimIndent()
            is DestinationFileMustBeUpdatedException -> """
                The destination file "${exception.destinationFile}"
                must be updated because its metadata such as last modified time is not equal to the source file's one:
                ${exception.destinationFileMetadata.lastModifiedAtMillis} ≠ ${exception.expectedMetadata.lastModifiedAtMillis}.
            """.trimIndent()
            is UnknownFileTypeOrMalformedMetadataException -> """
                Unknown file type (whether it's a file or directory) is encountered,
                or it's metadata is malformed: "${exception.filePath}".
            """.trimIndent()
        }

}
