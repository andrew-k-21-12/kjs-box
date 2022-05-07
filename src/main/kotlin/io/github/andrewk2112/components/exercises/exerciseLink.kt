package io.github.andrewk2112.components.exercises

import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.hooks.useAppContext
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.*
import react.router.dom.Link
import react.router.dom.LinkProps
import styled.css
import styled.styled

external interface ExerciseLinkProps : LinkProps {
    var text: String
}

val exerciseLink = fc<ExerciseLinkProps> { props ->

    val context = useAppContext()

    styled(Link).invoke(this) { // "this" refers to the current context (builder) to insert this element into

        css {
            +Theme.fontFaces.accent(context)
            fontSize       = Theme.fontSizes.adaptive3(context)
            overflowWrap   = OverflowWrap.breakWord
            textDecoration = TextDecoration.none
            color          = Theme.palette.action1(context)
            visited {
                color = Theme.palette.actionDimmed1(context)
            }
        }

        attrs {
            reloadDocument = props.reloadDocument
            replace        = props.replace
            state          = props.state
            to             = props.to
        }

        +props.text

    }

}
