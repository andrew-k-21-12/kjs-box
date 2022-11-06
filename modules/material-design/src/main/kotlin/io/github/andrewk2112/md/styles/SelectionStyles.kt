package io.github.andrewk2112.md.styles

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import kotlinx.css.backgroundColor

/**
 * Common styles for selection and highlighting.
 * */
object SelectionStyles : DynamicStyleSheet() {

    val simpleHighlightingAndSelection: DynamicCssProvider<Context> by dynamicCss {
        +TransitionStyles.fastTransition(::backgroundColor).rules
        hover {
            backgroundColor = Theme.palette.selection1(it)
        }
        active {
            backgroundColor = Theme.palette.selectionFocused1(it)
        }
    }

}
