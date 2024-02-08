package io.github.andrewk2112.utility.common.extensions

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

/**
 * Writes a [String] into the [file] - just a syntax sugar for [writeText].
 */
@Throws(FileNotFoundException::class, SecurityException::class, IOException::class)
fun String.writeTo(file: File) = file.writeText(this)
