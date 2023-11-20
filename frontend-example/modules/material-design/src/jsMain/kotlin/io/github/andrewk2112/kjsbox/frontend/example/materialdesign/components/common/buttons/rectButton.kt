package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.buttons

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.core.extensions.asMouseEventHandler
import io.github.andrewk2112.kjsbox.frontend.core.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles.*
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles.AnimationStyles.addTapHighlighting
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useAppContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.accessors.MaterialDesignTokens
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
            StrokeConfigs(it, StrokeColor.Custom(MaterialDesignTokens.system.palette::onSurface2SlightlyLighter))
        ).rules
        +SelectionStyles.simpleHighlightingAndSelection(it).rules
        color = MaterialDesignTokens.system.palette.onSurface2SlightlyLighter(it)

        hover {
            color = MaterialDesignTokens.system.palette.onSurface2Lighter(it)
        }

    }

    val darkButton: DynamicCssProvider<Context> by dynamicCss {

        +button(it).rules
        +StrokeStyles.borderStroke(
            StrokeConfigs(it, StrokeColor.Custom(MaterialDesignTokens.system.palette::onSurface1))
        ).rules
        +TransitionStyles.fastTransition(::backgroundColor).rules
        color = MaterialDesignTokens.system.palette.onSurface1(it)

        hover {
            +StrokeStyles.borderStroke(
                StrokeConfigs(it, StrokeColor.Custom(MaterialDesignTokens.system.palette::onSurface1Focused))
            ).rules
            color           = MaterialDesignTokens.system.palette.onSurface1Focused(it)
            backgroundColor = MaterialDesignTokens.system.palette.selection2Focused(it)
        }
        active {
            backgroundColor = MaterialDesignTokens.system.palette.selection2Active(it)
        }

    }

    val animationArea: NamedRuleSet by css {
        position = Position.relative // to put the label on top
        width  = 100.pct
        height = 100.pct
        padding = Padding(
            horizontal = MaterialDesignTokens.reference.spacing.absolute15,
            vertical   = MaterialDesignTokens.reference.spacing.absolute10,
        )
    }

    private val button: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.regular.rules
        position = Position.relative
        overflow = Overflow.hidden                                           // to cut the spreading of the animation
        fontSize = MaterialDesignTokens.reference.fontSizes.relative0p8
        backgroundColor = MaterialDesignTokens.reference.palette.transparent // resetting the default button color
        cursor = Cursor.pointer
    }

}
