package io.github.andrewk2112.utility.common.utility

/**
 * Simple [value] wrapper allowing to pass it by reference.
 */
class Reference<T>(var value: T) {
    override fun toString(): String = value.toString()
}
