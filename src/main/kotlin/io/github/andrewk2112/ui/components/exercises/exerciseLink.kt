package io.github.andrewk2112.ui.components.exercises

import history.To
import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.designtokens.stylesheets.DynamicCssProvider
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.extensions.setClassName
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.*
import react.router.dom.Link

// Public.

fun ChildrenBuilder.exerciseLink(context: Context, text: String, to: To) {

    // There is also a possibility to use CSS in a styled way, see previous commits.
    Link {

        setClassName(ExerciseLinkStyles.exerciseLink(context).name)
        this.to = to

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
