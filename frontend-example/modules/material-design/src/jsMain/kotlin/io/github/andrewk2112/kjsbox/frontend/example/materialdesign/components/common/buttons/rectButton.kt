package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.buttons

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.core.designtokens.StyleValues
import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Theme
import io.github.andrewk2112.kjsbox.frontend.core.extensions.asMouseEventHandler
import io.github.andrewk2112.kjsbox.frontend.core.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.hooks.useAppContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles.*
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles.AnimationStyles.addTapHighlighting
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import kotlinx.css.*
import react.ChildrenBuilder
import react.FC
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div



// Components.

val rectButton = FC<RectButtonProps> { props ->
    +button(
        with(RectButtonStyles) { if (props.isDark) darkButton else defaultButton }(useAppContext()).name,
        props.className.toString(),
    ) {
        onClick = props.action.asMouseEventHandler()
        animationAreaWithLabel(props.text)
    }
}

/**
 * The animation activation area with a [label] and spacing.
 */
private fun ChildrenBuilder.animationAreaWithLabel(label: String) =
    +div(clazz = RectButtonStyles.animationArea.name) {
        addTapHighlighting()
        +label.uppercase()
    }



// Styles.

private object RectButtonStyles : DynamicStyleSheet() {

    val defaultButton: DynamicCssProvider<Context> by dynamicCss {

        +button(it).rules
        +StrokeStyles.borderStroke(
            StrokeConfigs(it, StrokeColor.Custom(Theme.palette::onSurfaceSlightlyLighter2))
        ).rules
        +SelectionStyles.simpleHighlightingAndSelection(it).rules
        color = Theme.palette.onSurfaceSlightlyLighter2(it)

        hover {
            color = Theme.palette.onSurfaceLighter2(it)
        }

    }

    val darkButton: DynamicCssProvider<Context> by dynamicCss {

        +button(it).rules
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

    val animationArea: NamedRuleSet by css {
        position = Position.relative // to put the label on top
        width  = 100.pct
        height = 100.pct
        padding = Padding(
            horizontal = StyleValues.spacing.absolute15,
            vertical   = StyleValues.spacing.absolute10,
        )
    }

    private val button: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.regular.rules
        position = Position.relative
        overflow = Overflow.hidden                        // to cut the spreading of the animation
        fontSize = StyleValues.fontSizes.relativep8
        backgroundColor = StyleValues.palette.transparent // resetting the default button color
        cursor = Cursor.pointer
    }

}
