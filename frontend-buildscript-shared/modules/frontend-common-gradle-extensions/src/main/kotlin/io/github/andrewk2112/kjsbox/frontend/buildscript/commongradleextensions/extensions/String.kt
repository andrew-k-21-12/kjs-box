package io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.extensions

import org.apache.tools.ant.taskdefs.condition.Os
import java.io.File

/**
 * Decapitalizes the first char.
 */
fun String.decapitalize(): String = replaceFirstChar { if (it.isUpperCase()) it.lowercase() else it.toString() }

/**
 * Replaces all dots in the [String] with slashes.
 */
fun String.dotsToSlashes(): String = replace(".", "/")

/**
 * Syntax sugar for [prependIndent]: prepends an indent (the left side argument) for each line of the [source] string.
 */
infix fun String.indented(source: String): String = source.prependIndent(this)

/**
 * Applies a [modification] to the source [String] if it is not empty, returns the original empty [String] otherwise.
 */
inline fun String.modifyIfNotEmpty(modification: (String) -> String): String =
    if (isNotEmpty()) modification(this) else this

/**
 * Replaces the default system [File.separatorChar]s when running on [Os.FAMILY_WINDOWS]
 * with the conventional and working everywhere "/".
 */
fun String.toUniversalPathString(): String =
    if (Os.isFamily(Os.FAMILY_WINDOWS)) replace(File.separatorChar, '/') else this

/**
 * Cleans up and updates the source [String] to make it possible to be used as a valid Java/Kotlin package.
 */
fun String.toValidPackage(): String = replace("-", "").replace("/", ".")
