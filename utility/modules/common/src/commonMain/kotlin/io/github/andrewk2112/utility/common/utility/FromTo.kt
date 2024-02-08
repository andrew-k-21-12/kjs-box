package io.github.andrewk2112.utility.common.utility

/**
 * Wraps a simple [source] and its corresponding [destination].
 */
data class FromTo<S, D>(val source: S, val destination: D)
