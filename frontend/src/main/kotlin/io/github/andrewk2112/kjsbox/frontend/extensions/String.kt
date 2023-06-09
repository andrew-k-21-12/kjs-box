package io.github.andrewk2112.kjsbox.frontend.extensions

import org.apache.tools.ant.taskdefs.condition.Os
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

/**
 * Replaces all dots in the [String] with slashes.
 */
internal fun String.dotsToSlashes(): String = replace(".", "/")

/**
 * Syntax sugar for [prependIndent]: prepends an indent (the left side argument) for each line of the [source] string.
 */
internal infix fun String.indented(source: String): String = source.prependIndent(this)

/**
 * Applies a [modification] to the source [String] if it is not empty, returns the original empty [String] otherwise.
 */
internal inline fun String.modifyIfNotEmpty(modification: (String) -> String): String =
    if (isNotEmpty()) modification(this) else this

/**
 * Replaces the default system [File.separatorChar]s when running on [Os.FAMILY_WINDOWS]
 * with the conventional and working everywhere "/".
 */
internal fun String.toUniversalPathString(): String =
    if (Os.isFamily(Os.FAMILY_WINDOWS)) replace(File.separatorChar, '/') else this

/**
 * Cleans up and updates the source [String] to make it possible to be used as a valid Java/Kotlin package.
 */
internal fun String.toValidPackage(): String = replace("-", "").replace("/", ".")

/**
 * Writes a [String] into the [file] - just a syntax sugar for [writeText].
 */
@Throws(FileNotFoundException::class, SecurityException::class, IOException::class)
internal fun String.writeTo(file: File) = file.writeText(this)
