package io.github.andrewk2112.kjsbox.frontend.core.extensions

import kotlinx.css.hyphenize
import kotlinx.css.properties.Time
import kotlinx.css.properties.Timing
import kotlinx.css.properties.Transition
import kotlinx.css.properties.s
import kotlin.reflect.KProperty

/**
 * Creates a [Transition] for a property name correctly generated from the provided Kotlin [property].
 */
fun propertyTransition(
    property: KProperty<*>,
    duration: Time   = 0.s,
    timing:   Timing = Timing.ease,
    delay:    Time   = 0.s
) = Transition(property.name.hyphenize(), duration, timing, delay)
