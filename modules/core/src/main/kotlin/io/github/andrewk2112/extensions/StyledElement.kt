package io.github.andrewk2112.extensions

import kotlinx.css.StyledElement
import kotlin.reflect.KProperty

// Public.

/** A wrapper for the CSS [outline-style](https://developer.mozilla.org/docs/Web/CSS/outline-style) property. */
var StyledElement.outlineStyle: OutlineStyle by CssProperty()



// Private.

/**
 * Just a copy-paste from the original [kotlinx.css] package to make declarations of additional properties possible.
 * */
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
