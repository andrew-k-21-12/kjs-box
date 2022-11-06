package io.github.andrewk2112.md.styles

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.HasCssSuffix
import io.github.andrewk2112.extensions.OutlineStyle
import io.github.andrewk2112.extensions.camelCaseWord
import io.github.andrewk2112.extensions.outlineStyle
import kotlinx.css.*
import kotlinx.css.properties.*

// Utility - stroke color.

/**
 * A preferred coloring mode for a stroke.
 * */
enum class StrokeColor { DEFAULT, INTENSE, DARK }

/**
 * Loads a themed color for the corresponding [StrokeColor] type according to the provided [context].
 * */
private fun StrokeColor.getThemedColor(context: Context): Color = when (this) {
    StrokeColor.DEFAULT -> Theme.palette.stroke1(context)
    StrokeColor.INTENSE -> Theme.palette.stroke2(context)
    StrokeColor.DARK    -> Theme.palette.stroke3(context)
}



// Utility - stroke position.

/**
 * Denotes the position of a stroke.
 * */
enum class StrokePosition { LEFT, TOP, RIGHT, BOTTOM }

/** Lookup for the matching border styling function. */
private val StrokePosition.matchingStylingFunction: StyledElement.(LinearDimension, BorderStyle, Color) -> Unit
    get() = when (this) {
        StrokePosition.LEFT   -> StyledElement::borderLeft
        StrokePosition.TOP    -> StyledElement::borderTop
        StrokePosition.RIGHT  -> StyledElement::borderRight
        StrokePosition.BOTTOM -> StyledElement::borderBottom
    }



// Utility - all stroke configs wrapped together.

/**
 * All configs required to evaluate a stroke's style.
 * */
class StrokeConfigs(
    val context: Context,
    val color: StrokeColor,
    vararg val positions: StrokePosition = StrokePosition.values(),
) : HasCssSuffix {

    override val cssSuffix: String
        get() = context.cssSuffix +
                color.name.camelCaseWord() +
                positions
                    .sorted()
                    .joinToString(separator = "") { it.name.camelCaseWord() }

}



// Style builders.

/**
 * A compilation of stroke styles to be reused.
 * */
object StrokeStyles : DynamicStyleSheet() {

    /** Applies a border-based stroke. */
    val borderStroke: DynamicCssProvider<StrokeConfigs> by dynamicCss {
        val strokeWidth = Theme.sizes.stroke1(it.context)
        val strokeColor = it.color.getThemedColor(it.context)
        it.positions.map { position ->
            position.matchingStylingFunction.invoke(this, strokeWidth, BorderStyle.solid, strokeColor)
        }
    }

    /** Applies an outline-based stroke. */
    val outlineStroke: DynamicCssProvider<StrokeConfigs> by dynamicCss {
        outlineWidth = Theme.sizes.stroke1(it.context)
        outlineStyle = OutlineStyle.solid
        outlineColor = it.color.getThemedColor(it.context)
    }

}
