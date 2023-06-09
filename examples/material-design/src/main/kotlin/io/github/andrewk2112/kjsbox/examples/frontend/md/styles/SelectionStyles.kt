package io.github.andrewk2112.kjsbox.examples.frontend.md.styles

import io.github.andrewk2112.kjsbox.frontend.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.designtokens.StyleValues
import io.github.andrewk2112.kjsbox.frontend.designtokens.Theme
import io.github.andrewk2112.kjsbox.frontend.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.stylesheets.NamedRuleSet
import kotlinx.css.*

/**
 * Common styles for selection and highlighting.
 */
object SelectionStyles : DynamicStyleSheet() {

    // Public.

    val simpleActionHighlighting: DynamicCssProvider<Context> by dynamicCss {
        color   = Theme.palette.action3(it)
        opacity = StyleValues.opacities.p5
        hover {
            opacity = StyleValues.opacities.p4
        }
    }

    val simpleHighlightingAndSelection: DynamicCssProvider<Context> by dynamicCss {
        +TransitionStyles.fastTransition(::backgroundColor).rules
        hover {
            backgroundColor = Theme.palette.selectionFocused1(it)
        }
        active {
            backgroundColor = Theme.palette.selectionActive1(it)
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
        padding(StyleValues.spacing.absolute20)
        cursor = Cursor.pointer
    }

}
