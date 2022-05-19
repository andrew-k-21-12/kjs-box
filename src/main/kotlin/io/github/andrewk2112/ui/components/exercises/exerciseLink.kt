package io.github.andrewk2112.ui.components.exercises

import csstype.ClassName
import history.To
import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.designtokens.stylesheets.DynamicCssProvider
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.*
import react.router.dom.Link

// Public.

fun RBuilder.exerciseLink(context: Context, text: String, to: To) {

    // There is also a possibility to use CSS in a styled way, see previous commits.
    Link {

        attrs {
            className = ClassName(ExerciseLinkStyles.exerciseLink(context).name)
            this.to   = to
        }

        +text

    }

}



// Private.

private object ExerciseLinkStyles : DynamicStyleSheet() {

    val exerciseLink: DynamicCssProvider<Context> by dynamicCss {
        +StyleValues.fontFaces.comfortaa.rules
        fontSize       = Theme.fontSizes.adaptive3(it)
        overflowWrap   = OverflowWrap.breakWord
        textDecoration = TextDecoration.none
        color          = Theme.palette.action1(it)
        visited {
            color = Theme.palette.actionDimmed1(it)
        }
    }

}
