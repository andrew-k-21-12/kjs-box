package io.github.andrewk2112.md.components.common

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.md.styles.*
import io.github.andrewk2112.md.styles.AnimationStyles.addTapHighlighting
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import kotlinx.css.*
import react.FC
import react.dom.DOMProps
import react.dom.events.MouseEventHandler
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div

// Public.

external interface RectButtonProps : DOMProps {
    var text: String
    var action: () -> Unit
}

val rectButton = FC<RectButtonProps> { props ->

    val context = useAppContext()

    // Root button itself.
    +button(RectButtonStyles.rectButtonBase(context).name, props.className.toString()) {

        onClick = props.action.asDynamic() as? MouseEventHandler<*>

        // Animation activation area with spacing.
        +div(RectButtonStyles.animationActivationArea.name) {

            addTapHighlighting()

            +props.text.uppercase()

        }

    }

}



// Private.

private object RectButtonStyles : DynamicStyleSheet() {

    val rectButtonBase: DynamicCssProvider<Context> by dynamicCss {
        +StrokeStyles.borderStroke.invoke(StrokeConfigs(it, StrokeColor.DARK)).rules
        +SelectionStyles.simpleHighlightingAndSelection(it).rules
        position = Position.relative
        overflow = Overflow.hidden                        // to cut spreading of the animation
        fontSize = StyleValues.fontSizes.relativep875
        backgroundColor = StyleValues.palette.transparent // resetting the default button color
        color = Theme.palette.onSurfaceSlightlyLighter2(it)
        hover {
            color = Theme.palette.onSurfaceLighter2(it)
        }
        cursor = Cursor.pointer
    }

    val animationActivationArea: NamedRuleSet by css {
        position = Position.relative // to put the label on top
        width  = 100.pct
        height = 100.pct
        padding(vertical = StyleValues.spacing.absolute9, horizontal = StyleValues.spacing.absolute15)
    }

}
