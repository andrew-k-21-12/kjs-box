package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.reference.ReferenceOpacities
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.reference.ReferenceSpacing
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system.SystemPalette
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles.StrokeColor
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles.StrokeConfigs
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles.StrokeStyles
import kotlinx.css.*

/**
 * Reusable selection and highlighting styles.
 */
class MaterialDesignComponentSelectionStyles(
    private val referenceOpacities: ReferenceOpacities,
    private val referenceSpacing: ReferenceSpacing,
    private val systemPalette: SystemPalette,
    private val componentTransitionStyles: MaterialDesignComponentTransitionStyles
) : DynamicStyleSheet() {

    // Public.

    val simpleActionHighlighting: DynamicCssProvider<Context> by dynamicCss {
        color   = systemPalette.action2(it)
        opacity = referenceOpacities.half
        hover {
            opacity = referenceOpacities.p4
        }
    }

    val simpleHighlightingAndSelection: DynamicCssProvider<Context> by dynamicCss {
        +componentTransitionStyles.fast(::backgroundColor).rules
        hover {
            backgroundColor = systemPalette.selection1Focused(it)
        }
        active {
            backgroundColor = systemPalette.selection1Active(it)
        }
    }

    val hoverableWithDefaultPaddedStroke: DynamicCssProvider<Context> by dynamicCss {
        +hoverableWithPaddedStrokeBase.rules
        hover {
            +StrokeStyles.outlineStroke(StrokeConfigs(it, StrokeColor.Default)).rules
        }
    }

    val hoverableWithIntensePaddedStroke: DynamicCssProvider<Context> by dynamicCss {
        +hoverableWithPaddedStrokeBase.rules
        hover {
            +StrokeStyles.outlineStroke(StrokeConfigs(it, StrokeColor.Intense)).rules
        }
    }



    // Private.

    private val hoverableWithPaddedStrokeBase: NamedRuleSet by css {
        padding = Padding(referenceSpacing.absolute20)
        cursor = Cursor.pointer
    }

}
