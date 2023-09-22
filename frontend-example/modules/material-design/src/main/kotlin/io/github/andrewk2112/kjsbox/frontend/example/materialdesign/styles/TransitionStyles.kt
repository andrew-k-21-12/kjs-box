package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.StyleValues
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
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
