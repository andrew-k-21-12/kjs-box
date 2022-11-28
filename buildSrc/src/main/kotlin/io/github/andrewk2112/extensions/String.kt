package io.github.andrewk2112.extensions

import org.apache.tools.ant.taskdefs.condition.Os
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

/**
 * Replaces all dots in the [String] with slashes.
 */
internal fun String.dotsToSlashes(): String = replace(".", "/")

/**
 * Replaces all slashes in the [String] with dots.
 */
internal fun String.slashesToDots(): String = replace("/", ".")

/**
 * Replaces the default system [File.separatorChar]s when running on [Os.FAMILY_WINDOWS]
 * with the conventional and working everywhere "/".
 */
internal fun String.toUniversalPathString(): String =
    if (Os.isFamily(Os.FAMILY_WINDOWS)) replace(File.separatorChar, '/') else this

/**
 * Writes a [String] into the [file] - just a syntax sugar for [writeText].
 */
@Throws(FileNotFoundException::class, SecurityException::class, IOException::class)
internal fun String.writeTo(file: File) = file.writeText(this)
