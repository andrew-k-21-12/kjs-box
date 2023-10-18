package io.github.andrewk2112.utility.string.extensions

/**
 * Capitalizes the first char.
 */
fun String.capitalize(): String = replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

/**
 * Decapitalizes the first char.
 */
fun String.decapitalize(): String = replaceFirstChar { if (it.isUpperCase()) it.lowercase() else it.toString() }

/**
 * Syntax sugar for [prependIndent]: prepends an indent (the left side argument) for each line of the [source] string.
 */
infix fun String.indented(source: String): String = source.prependIndent(this)

/**
 * Applies a [modification] to a source [String] if it is not empty, returns the original empty [String] otherwise.
 */
inline fun String.modifyIfNotEmpty(modification: (String) -> String): String =
    if (isNotEmpty()) modification(this) else this
