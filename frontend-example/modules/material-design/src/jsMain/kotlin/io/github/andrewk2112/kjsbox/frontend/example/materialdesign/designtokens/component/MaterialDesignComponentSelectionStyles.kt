package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.reference.ReferenceOpacities
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.reference.ReferenceSpacing
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system.SystemPalette
import kotlinx.css.*

/**
 * Reusable selection and highlighting styles.
 */
class MaterialDesignComponentSelectionStyles(
    private val referenceOpacities: ReferenceOpacities,
    private val referenceSpacing: ReferenceSpacing,
    private val systemPalette: SystemPalette,
    private val componentStrokeStyles: MaterialDesignComponentStrokeStyles,
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
            +componentStrokeStyles.darkOutlineStroke(it).rules
        }
    }

    val hoverableWithIntensePaddedStroke: DynamicCssProvider<Context> by dynamicCss {
        +hoverableWithPaddedStrokeBase.rules
        hover {
            +componentStrokeStyles.lightOutlineStroke(it).rules
        }
    }



    // Private.

    private val hoverableWithPaddedStrokeBase: NamedRuleSet by css {
        padding = Padding(referenceSpacing.absolute20)
        cursor = Cursor.pointer
    }

}
