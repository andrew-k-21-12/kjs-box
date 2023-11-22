package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles

import io.github.andrewk2112.kjsbox.frontend.core.extensions.propertyTransition
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.accessors.materialDesignTokens
import kotlinx.css.transition
import kotlin.reflect.KProperty

/**
 * Contains some common transition configs.
 */
object TransitionStyles : DynamicStyleSheet() {

    val flashingTransition: DynamicCssProvider<KProperty<*>> by dynamicCss {
        transition += propertyTransition(
            it,
            materialDesignTokens.reference.time.ms100,
            materialDesignTokens.reference.timing.cubicBezier1
        )
    }

    val fastTransition: DynamicCssProvider<KProperty<*>> by dynamicCss {
        transition += propertyTransition(
            it,
            materialDesignTokens.reference.time.ms200,
            materialDesignTokens.reference.timing.cubicBezier1
        )
    }

}
