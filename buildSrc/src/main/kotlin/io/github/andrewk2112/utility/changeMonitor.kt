package io.github.andrewk2112.utility

import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty

/**
 * Initiates a property with a null value and notifies about each value update via the [onChange] callback.
 * */
internal inline fun <T> changeMonitor(crossinline onChange: () -> Unit) = object : ObservableProperty<T?>(null) {
    override fun afterChange(property: KProperty<*>, oldValue: T?, newValue: T?) {
        if (newValue != oldValue) {
            onChange()
        }
    }
}
