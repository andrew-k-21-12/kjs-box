package io.github.andrewk2112.versioncatalogsgenerator.utility

/**
 * Simple [value] wrapper allowing to pass it by reference.
 */
internal class Reference<T>(internal var value: T) {
    override fun toString(): String = value.toString()
}
