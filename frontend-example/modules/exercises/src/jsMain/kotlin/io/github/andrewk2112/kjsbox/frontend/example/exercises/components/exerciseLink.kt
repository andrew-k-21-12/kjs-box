package io.github.andrewk2112.kjsbox.frontend.example.exercises.components

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Theme
import io.github.andrewk2112.kjsbox.frontend.core.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.fonts.exercises.ComfortaaFontStyles
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.*
import react.router.dom.Link
import remix.run.router.To

// Public.

fun ChildrenBuilder.exerciseLink(context: Context, text: String, to: To) {
    +Link(clazz = ExerciseLinkStyles.link(context).name) {
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
