package io.github.andrewk2112.utility.common.utility

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * A [ReadOnlyProperty] delegate allowing to perform a value initialization lazily and reuse the same initialized value.
 *
 * Not thread-safe!
 *
 * @param initializer Describes how to initialize lazy value of this property.
 */
class LazyReadOnlyProperty<in T, out V>(initializer: (KProperty<*>) -> V) : ReadOnlyProperty<T, V> {

    // Public override.

    override fun getValue(thisRef: T, property: KProperty<*>): V {
        _initializer?.let {
            value        = it(property)
            _initializer = null
        }
        @Suppress("UNCHECKED_CAST") // it's safe as the value initialization lambda uses exactly the same return type
        return value as V
    }



    // Private.

    /** One time-firing initializer of for [value]. */
    private var _initializer: ((KProperty<*>) -> V)? = initializer

    /** The value itself provided by this delegate. */
    private var value: V? = null

}
