package io.github.andrewk2112.utility.string.extensions

/**
 * Joins all [String]s from the [Iterable] with applying capitalization for each [String].
 */
fun Iterable<String>.joinCapitalized(): String = joinToString(separator = "") { it.capitalize() }
