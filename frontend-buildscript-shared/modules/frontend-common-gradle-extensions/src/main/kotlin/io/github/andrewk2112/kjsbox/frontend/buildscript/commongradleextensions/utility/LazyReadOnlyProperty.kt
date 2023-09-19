package io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.utility

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * A [ReadOnlyProperty] delegate allowing to perform a value initialization lazily and reuse the same initialized value.
 *
 * Not thread-safe!
 *
 * @param initializer Describes how to initialize the lazy value of this property.
 */
class LazyReadOnlyProperty<in T, out V>(
    private inline val initializer: (KProperty<*>) -> V
) : ReadOnlyProperty<T, V> {

    // Public override.

    override fun getValue(thisRef: T, property: KProperty<*>): V {
        if (!isInitialized) {
            isInitialized = true
            value = initializer(property)
        }
        @Suppress("UNCHECKED_CAST") // it's safe as the value initialization lambda uses exactly the same return type
        return value as V
    }



    // Private.

    /** Whether the [value] initialization was performed. */
    private var isInitialized = false

    /** The value itself provided by this delegate. */
    private var value: V? = null

}
