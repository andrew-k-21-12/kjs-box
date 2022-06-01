package io.github.andrewk2112.ui.components.md.menu

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.RBuilder
import react.dom.attrs
import react.dom.html.AnchorTarget
import styled.css
import styled.styledA

fun RBuilder.menuItem(context: Context, itemName: String, itemDestination: String) {

    styledA {

        attrs {
            target = AnchorTarget._blank.toString()
            href   = itemDestination
        }

        css {
            display = Display.inlineBlock
            width   = 100.pct
            marginBottom  = 9.px // FIXME
            paddingLeft   = StyleValues.spacing.absolute24
            paddingTop    = 12.px // FIXME
            paddingBottom = 12.px // FIXME
            fontSize       = 0.875.rem // FIXME
            textDecoration = TextDecoration.none
            hover {
                backgroundColor = Color.red // FIXME
            }
        }

        +itemName

    }

}
