package io.github.andrewk2112.md.components.common

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.extensions.asMouseEventHandler
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.md.styles.*
import io.github.andrewk2112.md.styles.AnimationStyles.addTapHighlighting
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import kotlinx.css.*
import react.FC
import react.PropsWithClassName
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div

// Public.

external interface RectButtonProps : PropsWithClassName {
    var text: String
    var action: () -> Unit
    var isDark: Boolean
}

val rectButton = FC<RectButtonProps> { props ->

    val context = useAppContext()

    // Root button itself.
    +button(
        with(RectButtonStyles) { if (props.isDark) darkRectButton else defaultRectButton }.invoke(context).name,
        props.className.toString(),
    ) {

        onClick = props.action.asMouseEventHandler()

        // Animation activation area with spacing.
        +div(RectButtonStyles.animationActivationArea.name) {

            addTapHighlighting()

            +props.text.uppercase()

        }

    }

}



// Private.

private object RectButtonStyles : DynamicStyleSheet() {

    // Public.

    val defaultRectButton: DynamicCssProvider<Context> by dynamicCss {
        +rectButtonBase(it).rules
        +StrokeStyles.borderStroke(
            StrokeConfigs(it, StrokeColor.Custom(Theme.palette::onSurfaceSlightlyLighter2))
        ).rules
        +SelectionStyles.simpleHighlightingAndSelection(it).rules
        color = Theme.palette.onSurfaceSlightlyLighter2(it)
        hover {
            color = Theme.palette.onSurfaceLighter2(it)
        }
    }

    val darkRectButton: DynamicCssProvider<Context> by dynamicCss {
        +rectButtonBase(it).rules
        +StrokeStyles.borderStroke(
            StrokeConfigs(it, StrokeColor.Custom(Theme.palette::onSurface1))
        ).rules
        +TransitionStyles.fastTransition(::backgroundColor).rules
        color = Theme.palette.onSurface1(it)
        hover {
            +StrokeStyles.borderStroke(
                StrokeConfigs(it, StrokeColor.Custom(Theme.palette::onSurfaceFocused1))
            ).rules
            color           = Theme.palette.onSurfaceFocused1(it)
            backgroundColor = Theme.palette.selectionFocused2(it)
        }
        active {
            backgroundColor = Theme.palette.selectionActive2(it)
        }
    }

    val animationActivationArea: NamedRuleSet by css {
        position = Position.relative // to put the label on top
        width  = 100.pct
        height = 100.pct
        padding(vertical = StyleValues.spacing.absolute10, horizontal = StyleValues.spacing.absolute15)
    }



    // Private.

    private val rectButtonBase: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.regular.rules
        position = Position.relative
        overflow = Overflow.hidden                        // to cut spreading of the animation
        fontSize = StyleValues.fontSizes.relativep8
        backgroundColor = StyleValues.palette.transparent // resetting the default button color
        cursor = Cursor.pointer
    }

}
