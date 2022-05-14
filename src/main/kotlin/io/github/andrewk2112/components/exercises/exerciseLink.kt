package io.github.andrewk2112.components.exercises

import csstype.ClassName
import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.designtokens.stylesheets.DynamicCssProvider
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.hooks.useAppContext
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.*
import react.router.dom.Link
import react.router.dom.LinkProps

// Public.

external interface ExerciseLinkProps : LinkProps {
    var text: String
}

val exerciseLink = fc<ExerciseLinkProps> { props ->

    val context = useAppContext()

    // There is also a possibility to use CSS in a styled way, see the previous commit.
    Link {

        attrs {
            className      = ClassName(ExerciseLinkStyles.exerciseLink(context).name)
            reloadDocument = props.reloadDocument
            replace        = props.replace
            state          = props.state
            to             = props.to
        }

        +props.text

    }

}



// Private.

private object ExerciseLinkStyles : DynamicStyleSheet() {

    val exerciseLink: DynamicCssProvider<Context> by dynamicCss {
        +Theme.fontFaces.accent(it).rules
        fontSize       = Theme.fontSizes.adaptive3(it)
        overflowWrap   = OverflowWrap.breakWord
        textDecoration = TextDecoration.none
        color          = Theme.palette.action1(it)
        visited {
            color = Theme.palette.actionDimmed1(it)
        }
    }

}
