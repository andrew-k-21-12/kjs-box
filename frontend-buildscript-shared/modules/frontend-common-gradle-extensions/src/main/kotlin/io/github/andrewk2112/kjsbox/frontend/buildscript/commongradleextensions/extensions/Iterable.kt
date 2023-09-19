package io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.extensions

import org.gradle.configurationcache.extensions.capitalized

/**
 * Joins all [String]s from the [Iterable] with applying capitalization for each [String].
 */
fun Iterable<String>.joinCapitalized(): String = joinToString(separator = "") { it.capitalized() }
