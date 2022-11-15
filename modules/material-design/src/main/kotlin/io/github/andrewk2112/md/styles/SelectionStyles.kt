package io.github.andrewk2112.md.styles

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import kotlinx.css.Cursor
import kotlinx.css.backgroundColor
import kotlinx.css.cursor
import kotlinx.css.padding

/**
 * Common styles for selection and highlighting.
 * */
object SelectionStyles : DynamicStyleSheet() {

    // Public.

    val simpleHighlightingAndSelection: DynamicCssProvider<Context> by dynamicCss {
        +TransitionStyles.fastTransition(::backgroundColor).rules
        hover {
            backgroundColor = Theme.palette.selection1(it)
        }
        active {
            backgroundColor = Theme.palette.selectionFocused1(it)
        }
    }

    val hoverableWithDefaultPaddedStroke: DynamicCssProvider<Context> by dynamicCss {
        +hoverableWithPaddedStrokeBase.rules
        hover {
            +StrokeStyles.outlineStroke(StrokeConfigs(it, StrokeColor.DEFAULT)).rules
        }
    }

    val hoverableWithIntensePaddedStroke: DynamicCssProvider<Context> by dynamicCss {
        +hoverableWithPaddedStrokeBase.rules
        hover {
            +StrokeStyles.outlineStroke(StrokeConfigs(it, StrokeColor.INTENSE)).rules
        }
    }



    // Private.

    private val hoverableWithPaddedStrokeBase: NamedRuleSet by css {
        padding(StyleValues.spacing.absolute20)
        cursor = Cursor.pointer
    }

}
