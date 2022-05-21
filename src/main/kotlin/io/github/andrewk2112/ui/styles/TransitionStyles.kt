package io.github.andrewk2112.ui.styles

import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import kotlinx.css.properties.cubicBezier
import kotlinx.css.properties.ms
import kotlinx.css.properties.transition
import kotlin.reflect.KProperty

/**
 * Contains some common transition configs.
 * */
object TransitionStyles : DynamicStyleSheet() {

    val defaultTransition by dynamicCss<KProperty<*>> {
        transition(it, 100.ms, cubicBezier(0.4, 0.0, 0.2, 1.0))
    }

}
