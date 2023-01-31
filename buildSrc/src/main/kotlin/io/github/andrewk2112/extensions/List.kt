package io.github.andrewk2112.extensions

/**
 * Returns a slice of elements starting (inclusive) from the [fromIndex]
 * and containing all the rest elements after this [fromIndex].
 */
internal fun <T> List<T>.subList(fromIndex: Int): List<T> = subList(fromIndex, size)
