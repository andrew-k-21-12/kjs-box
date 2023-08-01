package io.github.andrewk2112.kjsbox.frontend.dsl.utility

/**
 * Wraps a simple [source] and its corresponding [destination].
 */
internal data class FromTo<S, D>(internal val source: S, internal val destination: D)
