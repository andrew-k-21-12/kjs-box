package io.github.andrewk2112.kjsbox.frontend.buildscript.shared.gradle.tasks.actions.writeintodirectory

import io.github.andrewk2112.utility.common.extensions.joinWithPath
import io.github.andrewk2112.utility.common.utility.Result
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.nio.charset.Charset

/**
 * Writes some [textToWrite] to the file named as [destinationFileName] inside the destination directory.
 */
class TextWriteIntoDirectoryAction(
    private val textToWrite: String,
    private val destinationFileName: String,
) : SimpleWriteIntoDirectoryAction {

    override fun writeIntoDirectory(directory: File): Result<Unit, Exception> {
        var output: OutputStream? = null
        return try {
            output = FileOutputStream(directory.joinWithPath(destinationFileName))
            IOUtils.write(textToWrite, output, Charset.defaultCharset())
            output.flush()
            Result.Success(Unit)
        } catch (exception: Exception) {
            Result.Failure(exception)
        } finally {
            IOUtils.closeQuietly(output)
        }
    }

}
