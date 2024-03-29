package io.github.andrewk2112.utility.common.extensions

/**
 * Returns a slice of elements as a typed [Array] starting (inclusive) from the [fromIndex]
 * and containing all the rest elements after this [fromIndex].
 */
inline fun <reified T> List<T>.subArray(fromIndex: Int): Array<T> = subList(fromIndex).toTypedArray()

/**
 * Returns a slice of elements starting (inclusive) from the [fromIndex]
 * and containing all the rest elements after this [fromIndex].
 */
fun <T> List<T>.subList(fromIndex: Int): List<T> = subList(fromIndex, size)
