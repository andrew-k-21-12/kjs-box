package io.github.andrewk2112.components.exercises

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
import styled.css
import styled.styled

// Public.

external interface ExerciseLinkProps : LinkProps {
    var text: String
}

val exerciseLink = fc<ExerciseLinkProps> { props ->

    val context = useAppContext()

    styled(Link).invoke(this) { // "this" refers to the current context (builder) to insert this element into

        css(ExerciseLinkStyles.exerciseLink(context).rules)

        attrs {
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
