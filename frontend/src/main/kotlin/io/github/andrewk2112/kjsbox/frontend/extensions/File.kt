package io.github.andrewk2112.kjsbox.frontend.extensions

import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.InvalidPathException

/**
 * Concatenates the current directory with a new [path] - just syntax sugar.
 */
fun File.joinWithPath(path: String) = File(this, path)

/**
 * Creates a symbolic link from this [File] to the [target],
 * makes sure all intermediate directories to the current [File] exist, makes them if needed.
 */
@Throws(
    FileAlreadyExistsException::class,
    InvalidPathException::class,
    IOException::class,
    SecurityException::class,
    UnsupportedOperationException::class,
)
internal fun File.createSymbolicLinkTo(target: File) {
    if (exists()) return
    parentFile?.ensureDirectoryExistsOrThrow("Could not create an intermediate path to $absolutePath")
    Files.createSymbolicLink(toPath(), target.toPath())
}

/**
 * Creates the directory if it doesn't exist.
 *
 * @param errorMessage A message to be used in the [IOException] in the case of failure.
 *
 * @throws IOException When the directory doesn't exist and can not be created.
 */
@Throws(IOException::class)
internal fun File.ensureDirectoryExistsOrThrow(errorMessage: String) {
    if (!isDirectory && !mkdirs()) {
        throw IOException(errorMessage)
    }
}
