package io.github.andrewk2112.exercises.components

import history.To
import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.resources.fonts.exercises.ComfortaaFontStyles
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.*
import react.router.dom.Link

// Public.

fun ChildrenBuilder.exerciseLink(context: Context, text: String, to: To) {
    +Link(ExerciseLinkStyles.link(context).name) {
        this.to = to
        +text
    }
}



// Private.

private object ExerciseLinkStyles : DynamicStyleSheet() {

    val link: DynamicCssProvider<Context> by dynamicCss {
        +ComfortaaFontStyles.regular.rules
        fontSize       = Theme.fontSizes.adaptive4(it)
        overflowWrap   = OverflowWrap.breakWord
        textDecoration = TextDecoration.none
        color          = Theme.palette.action1(it)
        visited {
            color = Theme.palette.actionDimmed1(it)
        }
    }

}
