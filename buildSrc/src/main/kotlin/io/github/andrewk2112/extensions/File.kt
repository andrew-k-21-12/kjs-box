package io.github.andrewk2112.extensions

import java.io.File
import java.io.IOException

/**
 * Creates the directory if it doesn't exist.
 *
 * @param errorMessage A message to be used in the [IOException] in the case of failure.
 *
 * @throws IOException When the directory doesn't exist and can not be created.
 * */
@Throws(IOException::class)
internal fun File.ensureDirectoryExistsOrThrow(errorMessage: String) {
    if (!isDirectory && !mkdirs()) {
        throw IOException(errorMessage)
    }
}

/**
 * Concatenates the current directory with a new [path] - just syntax sugar.
 * */
internal fun File.joinWithPath(path: String) = File(this, path)
