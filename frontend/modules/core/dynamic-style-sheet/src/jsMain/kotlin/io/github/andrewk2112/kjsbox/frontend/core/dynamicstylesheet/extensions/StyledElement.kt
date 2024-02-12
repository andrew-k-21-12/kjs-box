package io.github.andrewk2112.kjsbox.frontend.core.dynamicstylesheet.extensions

import kotlinx.css.StyledElement
import kotlinx.css.hyphenize
import kotlinx.css.properties.Time
import kotlinx.css.properties.Timing
import kotlinx.css.properties.Transition
import kotlinx.css.properties.s
import kotlinx.css.transition
import kotlin.reflect.KProperty

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
