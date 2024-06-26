package io.github.andrewk2112.utility.common.utility

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * This is a property delegate requiring its value to be set before any usage.
 *
 * @throws IllegalStateException On attempt to read the property's value before it is set.
 */
open class NotNullVar<in T, V : Any> : ReadWriteProperty<T, V> {

    /**
     * See [ReadWriteProperty.getValue].
     *
     * @throws IllegalStateException On attempt to read the property's value before it is set.
     */
    override fun getValue(thisRef: T, property: KProperty<*>): V =
        backingValue ?: throw IllegalStateException(
            "Initialize the ${property.name} property" +
                    (thisRef?.let { " inside the ${it::class.simpleName} " } ?: " ") +
                    "before accessing it"
        )

    override fun setValue(thisRef: T, property: KProperty<*>, value: V) {
        backingValue = value
    }

    protected var backingValue: V? = null

}
