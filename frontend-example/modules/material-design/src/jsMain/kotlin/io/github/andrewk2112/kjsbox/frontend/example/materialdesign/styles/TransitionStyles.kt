package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component

import io.github.andrewk2112.kjsbox.frontend.core.extensions.propertyTransition
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.reference.ReferenceTime
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.reference.ReferenceTiming
import kotlinx.css.transition
import kotlin.reflect.KProperty

/**
 * Reusable transition styles.
 */
class MaterialDesignComponentTransitionStyles(
    private val referenceTime: ReferenceTime,
    private val referenceTiming: ReferenceTiming,
) : DynamicStyleSheet() {

    val flashing: DynamicCssProvider<KProperty<*>> by dynamicCss {
        transition += propertyTransition(
            it,
            referenceTime.ms100,
            referenceTiming.cubicBezier1
        )
    }

    val fast: DynamicCssProvider<KProperty<*>> by dynamicCss {
        transition += propertyTransition(
            it,
            referenceTime.ms200,
            referenceTiming.cubicBezier1
        )
    }

}
