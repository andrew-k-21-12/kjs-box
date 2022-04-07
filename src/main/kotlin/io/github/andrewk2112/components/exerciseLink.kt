package io.github.andrewk2112.components

import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.hooks.useAppContext
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.*
import react.dom.attrs
import react.router.dom.LinkProps
import styled.css
import styled.styledA

external interface ExerciseLinkProps : LinkProps {
    var text: String
}

val exerciseLink = fc<ExerciseLinkProps> { props ->

    val context = useAppContext()

    styledA {

        attrs {
            css {
                +Theme.fontFaces.accent.get(context)
                fontSize       = Theme.fontSizes.adaptive1.get(context)
                overflowWrap   = OverflowWrap.breakWord
                textDecoration = TextDecoration.none
                color          = Theme.palette.action1.get(context)
                visited {
                    color = Theme.palette.actionDimmed1.get(context)
                }
            }
            href = props.to
        }

        +props.text

    }

}
