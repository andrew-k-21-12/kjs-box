package io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.gradle.tasks.actions.writeintodirectory

import io.github.andrewk2112.utility.common.extensions.joinWithPath
import io.github.andrewk2112.utility.common.utility.Result
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

/**
 * Writes a built-in resource into the destination directory, doesn't preserve the intermediate resource path.
 */
@JvmInline
value class ResourceWriteIntoDirectoryAction(
    private val resourceName: String
) : SimpleWriteIntoDirectoryAction {

    override fun writeIntoDirectory(directory: File): Result<Unit, Exception> {
        val input = javaClass.classLoader.getResourceAsStream(resourceName)
                 ?: return Result.Failure(IllegalArgumentException("No `$resourceName` resource found"))
        var output: OutputStream? = null
        return try {
            output = FileOutputStream(directory.joinWithPath(File(resourceName).name))
            IOUtils.copy(input, output)
            output.flush()
            Result.Success(Unit)
        } catch (exception: Exception) {
            Result.Failure(exception)
        } finally {
            IOUtils.closeQuietly(input, output)
        }
    }

}
