package io.github.andrewk2112.kjsbox.frontend.core.extensions

import kotlinx.css.StyledElement
import kotlinx.css.hyphenize
import kotlinx.css.properties.Time
import kotlinx.css.properties.Timing
import kotlinx.css.properties.Transition
import kotlinx.css.properties.s
import kotlinx.css.transition
import kotlin.reflect.KProperty



// Public.

/**
 * Creates and adds a [Transition] for a property name correctly generated from the provided Kotlin [property].
 */
fun StyledElement.transition(
    property: KProperty<*>,
    duration: Time   = 0.s,
    timing:   Timing = Timing.ease,
    delay:    Time   = 0.s
) {
    transition += Transition(property.name.hyphenize(), duration, timing, delay)
}



// Private.

/**
 * Just a copy-paste from the original `kotlinx.css` package
 * to make declarations of additional CSS properties (currently missing in the Kotlin Wrappers library) possible.
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
