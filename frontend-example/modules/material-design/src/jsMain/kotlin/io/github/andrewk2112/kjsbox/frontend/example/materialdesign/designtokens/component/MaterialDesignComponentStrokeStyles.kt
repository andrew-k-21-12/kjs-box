package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.HasCssSuffix
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system.SystemPalette
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system.SystemSizes
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component.BorderContext.Position.*
import io.github.andrewk2112.utility.string.formats.cases.CamelCase
import kotlinx.css.*
import kotlin.reflect.KMutableProperty1



// Public.

/**
 * Border-related context to state [positions] to apply border styling to.
 */
class BorderContext(
    val context: Context,
    vararg val positions: Position = Position.entries.toTypedArray(),
) : HasCssSuffix {

    enum class Position { LEFT, TOP, RIGHT, BOTTOM }

    override val cssSuffix: String
        get() = context.cssSuffix +
                positions
                    .map { it.name }
                    .sorted()
                    .let { CamelCase.joinWords(it) }

}

/**
 * Reusable stroke styles.
 */
class MaterialDesignComponentStrokeStyles(
    private val systemPalette: SystemPalette,
    private val systemSizes: SystemSizes
) : DynamicStyleSheet() {

    /** Border style without coloring. */
    val borderStroke: DynamicCssProvider<BorderContext> by dynamicCss {
        val border = Border(systemSizes.stroke(it.context), BorderStyle.solid)
        it.positions.forEach { position ->
            position.borderProperty.set(this, border)
        }
    }

    val lightBorderStroke: DynamicCssProvider<BorderContext> by dynamicCss {
        +borderStroke(it).rules
        val color = systemPalette.stroke2(it.context)
        it.positions.forEach { position ->
            position.borderColorProperty.set(this, color)
        }
    }

    val darkBorderStroke: DynamicCssProvider<BorderContext> by dynamicCss {
        +borderStroke(it).rules
        val color = systemPalette.stroke1(it.context)
        it.positions.forEach { position ->
            position.borderColorProperty.set(this, color)
        }
    }

    /** Outline style without coloring. */
    val outlineStroke: DynamicCssProvider<Context> by dynamicCss {
        outlineWidth = systemSizes.stroke(it)
        outlineStyle = OutlineStyle.solid
    }

    val lightOutlineStroke: DynamicCssProvider<Context> by dynamicCss {
        +outlineStroke(it).rules
        outlineColor = systemPalette.stroke2(it)
    }

    val darkOutlineStroke: DynamicCssProvider<Context> by dynamicCss {
        +outlineStroke(it).rules
        outlineColor = systemPalette.stroke1(it)
    }

}



// Private.

/** Retrieves a matching border styling property. */
private val BorderContext.Position.borderProperty: KMutableProperty1<StyledElement, Border>
    get() = when (this) {
        LEFT   -> StyledElement::borderLeft
        TOP    -> StyledElement::borderTop
        RIGHT  -> StyledElement::borderRight
        BOTTOM -> StyledElement::borderBottom
    }

/** Retrieves a matching border color styling property. */
private val BorderContext.Position.borderColorProperty: KMutableProperty1<StyledElement, Color>
    get() = when (this) {
        LEFT   -> StyledElement::borderLeftColor
        TOP    -> StyledElement::borderTopColor
        RIGHT  -> StyledElement::borderRightColor
        BOTTOM -> StyledElement::borderBottomColor
    }
