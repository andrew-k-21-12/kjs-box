package io.github.andrewk2112.extensions

import kotlinx.css.StyledElement
import kotlin.reflect.KProperty

// Helps to add new CSS properties currently missing in the Kotlin Wrappers library.

/**
 * Just a copy-paste from the original [kotlinx.css] package to make declarations of additional properties possible.
 */
private class CssProperty<T>(private val default: (() -> T)? = null) {

    operator fun getValue(thisRef: StyledElement, property: KProperty<*>): T {
        default?.let { default ->
            if (!thisRef.declarations.containsKey(property.name)) {
                thisRef.declarations[property.name] = default() as Any
            }
        }
        @Suppress("UNCHECKED_CAST")
        return thisRef.declarations[property.name] as T
    }

    operator fun setValue(thisRef: StyledElement, property: KProperty<*>, value: T) {
        thisRef.declarations[property.name] = value as Any
    }

}
