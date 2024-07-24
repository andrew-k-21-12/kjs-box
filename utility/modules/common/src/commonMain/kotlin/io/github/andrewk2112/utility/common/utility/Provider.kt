package io.github.andrewk2112.utility.common.utility

/**
 * Simple getter for values which can not be retrieved directly.
 */
fun interface Provider<T> {
    fun get(): T
}
