package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.accessors.materialDesignTokens
import kotlinx.css.*

/**
 * Common styles for selection and highlighting.
 */
object SelectionStyles : DynamicStyleSheet() {

    // Public.

    val simpleActionHighlighting: DynamicCssProvider<Context> by dynamicCss {
        color   = materialDesignTokens.system.palette.action2(it)
        opacity = materialDesignTokens.reference.opacities.half
        hover {
            opacity = materialDesignTokens.reference.opacities.p4
        }
    }

    val simpleHighlightingAndSelection: DynamicCssProvider<Context> by dynamicCss {
        +TransitionStyles.fastTransition(::backgroundColor).rules
        hover {
            backgroundColor = materialDesignTokens.system.palette.selection1Focused(it)
        }
        active {
            backgroundColor = materialDesignTokens.system.palette.selection1Active(it)
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
        padding = Padding(materialDesignTokens.reference.spacing.absolute20)
        cursor = Cursor.pointer
    }

}
