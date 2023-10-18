package io.github.andrewk2112.kjsbox.frontend.core.designtokens.reference

import kotlinx.css.properties.ms
import kotlinx.css.properties.Time as CssTime

/**
 * A palette of all available time values.
 */
class Time {
    val immediate: CssTime get() = 0.ms
    val ms100:     CssTime get() = 100.ms
    val ms200:     CssTime get() = 200.ms
    val ms300:     CssTime get() = 300.ms
    val ms600:     CssTime get() = 600.ms
}
