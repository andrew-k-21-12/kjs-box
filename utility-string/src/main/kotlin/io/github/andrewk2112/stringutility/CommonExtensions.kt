package io.github.andrewk2112.stringutility

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
