package io.github.andrewk2112.kjsbox.examples.frontend.md.styles

import io.github.andrewk2112.kjsbox.frontend.designtokens.StyleValues
import io.github.andrewk2112.kjsbox.frontend.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.stylesheets.DynamicStyleSheet
import kotlinx.css.properties.transition
import kotlin.reflect.KProperty

/**
 * Contains some common transition configs.
 */
object TransitionStyles : DynamicStyleSheet() {

    val flashingTransition: DynamicCssProvider<KProperty<*>> by dynamicCss {
        transition(it, StyleValues.time.ms100, StyleValues.timing.cubicBezier1)
    }

    val fastTransition: DynamicCssProvider<KProperty<*>> by dynamicCss {
        transition(it, StyleValues.time.ms200, StyleValues.timing.cubicBezier1)
    }

}