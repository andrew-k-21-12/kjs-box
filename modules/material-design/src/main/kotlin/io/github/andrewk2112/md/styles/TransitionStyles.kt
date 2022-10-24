package io.github.andrewk2112.md.styles

import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import kotlinx.css.properties.transition
import kotlin.reflect.KProperty

/**
 * Contains some common transition configs.
 * */
object TransitionStyles : DynamicStyleSheet() {

    val defaultTransition: DynamicCssProvider<KProperty<*>> by dynamicCss {
        transition(it, StyleValues.time.ms100, StyleValues.timing.cubicBezier1)
    }

}
