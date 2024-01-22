package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.buttons

import io.github.andrewk2112.kjsbox.frontend.core.extensions.asMouseEventHandler
import io.github.andrewk2112.kjsbox.frontend.core.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.hooks.useMemoWithReferenceCount
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.useDesignTokensContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.surfaces.rippleSurface
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.useMaterialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component.BorderContext
import kotlinx.css.*
import react.ChildrenBuilder
import react.FC
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.span



// Components.

val rectButton = FC<RectButtonProps> { props ->

    val component = useMaterialDesignComponent()
    val styles    = useMemoWithReferenceCount(component) { RectButtonStyles(component.getMaterialDesignTokens()) }

    +button(
        with(styles) { if (props.isDark) darkButton else defaultButton }(useDesignTokensContext()).name,
        props.className.toString(),
    ) {
        onClick = props.action.asMouseEventHandler()
        animationAreaWithLabel(styles, props.text)
    }

}

/**
 * Animation activation area with a [label] and spacing.
 *
 * Otherwise, the button intercepts touch and mouse listeners.
 */
private fun ChildrenBuilder.animationAreaWithLabel(styles: RectButtonStyles, label: String) =
    +rippleSurface(clazz = styles.animationArea.name) {
        +span(clazz = styles.label.name) {
            +label.uppercase()
        }
    }



// Styles.

private class RectButtonStyles(
    private val materialDesignTokens: MaterialDesignTokens
) : DynamicStyleSheet(materialDesignTokens::class) {

    val defaultButton: DynamicCssProvider<Context> by dynamicCss {

        +button(it).rules
        +materialDesignTokens.component.stroke.borderStroke(BorderContext(it)).rules
        borderColor = materialDesignTokens.system.palette.onSurface2SlightlyLighter(it)
        +materialDesignTokens.component.selection.simpleHighlightingAndSelection(it).rules
        color = materialDesignTokens.system.palette.onSurface2SlightlyLighter(it)

        hover {
            color = materialDesignTokens.system.palette.onSurface2Lighter(it)
        }

    }

    val darkButton: DynamicCssProvider<Context> by dynamicCss {

        +button(it).rules
        +materialDesignTokens.component.stroke.borderStroke(BorderContext(it)).rules
        borderColor = materialDesignTokens.system.palette.onSurface1(it)
        +materialDesignTokens.component.transition.fast(::backgroundColor).rules
        color = materialDesignTokens.system.palette.onSurface1(it)

        hover {
            +materialDesignTokens.component.stroke.borderStroke(BorderContext(it)).rules
            borderColor = materialDesignTokens.system.palette.onSurface1Focused(it)
            color           = materialDesignTokens.system.palette.onSurface1Focused(it)
            backgroundColor = materialDesignTokens.system.palette.selection2Focused(it)
        }
        active {
            backgroundColor = materialDesignTokens.system.palette.selection2Active(it)
        }

    }

    val animationArea: NamedRuleSet by css {
        width  = 100.pct
        height = 100.pct
        padding = Padding(
            horizontal = materialDesignTokens.reference.spacing.absolute15,
            vertical   = materialDesignTokens.reference.spacing.absolute10,
        )
    }

    val label: NamedRuleSet by css {
        position = Position.relative // to put the label on top
    }

    private val button: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.system.fontStyles.regular(it).rules
        position = Position.relative
        overflow = Overflow.hidden                                           // to cut the spreading of the animation
        fontSize = materialDesignTokens.reference.fontSizes.relative0p8
        backgroundColor = materialDesignTokens.reference.palette.transparent // resetting the default button color
        cursor = Cursor.pointer
    }

}
