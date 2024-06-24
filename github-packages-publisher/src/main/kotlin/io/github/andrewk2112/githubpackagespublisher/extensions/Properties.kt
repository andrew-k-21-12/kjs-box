package io.github.andrewk2112.githubpackagespublisher.extensions

import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.Properties

/**
 * Constructs an instance of [Properties] by reading a file at [propertiesFilePath].
 */
@Throws(FileNotFoundException::class, IOException::class)
internal fun Properties(propertiesFilePath: String) = Properties().apply {
    FileInputStream(propertiesFilePath).use {
        load(it)
    }
}
