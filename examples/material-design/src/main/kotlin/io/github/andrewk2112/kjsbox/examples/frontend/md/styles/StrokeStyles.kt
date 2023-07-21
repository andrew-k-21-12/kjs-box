package io.github.andrewk2112.kjsbox.examples.frontend.md.styles

import io.github.andrewk2112.kjsbox.frontend.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.designtokens.Theme
import io.github.andrewk2112.kjsbox.frontend.designtokens.system.ThemedColor
import io.github.andrewk2112.kjsbox.frontend.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.stylesheets.HasCssSuffix
import io.github.andrewk2112.kjsbox.frontend.extensions.camelCaseWord
import io.github.andrewk2112.kjsbox.frontend.extensions.capitalize
import kotlinx.css.*
import kotlinx.css.properties.*
import kotlin.reflect.KProperty0



// Utility - stroke color.

/**
 * A preferred coloring mode for the stroke.
 */
sealed class StrokeColor {
    object Default : StrokeColor()
    object Intense : StrokeColor()
    class Custom(val colorReference: KProperty0<ThemedColor>) : StrokeColor()
}

/**
 * Loads a themed color for the corresponding [StrokeColor] type according to the provided [context].
 */
private fun StrokeColor.getThemedColor(context: Context): Color = when (this) {
    StrokeColor.Default   -> Theme.palette.stroke1(context)
    StrokeColor.Intense   -> Theme.palette.stroke2(context)
    is StrokeColor.Custom -> colorReference.get().invoke(context)
}



// Utility - stroke position.

/**
 * Denotes the position of a stroke.
 */
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
 */
class StrokeConfigs(
    val context: Context,
    val color: StrokeColor,
    vararg val positions: StrokePosition = StrokePosition.values(),
) : HasCssSuffix {

    override val cssSuffix: String
        get() = context.cssSuffix +
                color::class.simpleName + ((color as? StrokeColor.Custom)?.colorReference?.name?.capitalize() ?: "") +
                positions
                    .sorted()
                    .joinToString(separator = "") { it.name.camelCaseWord() }

}



// Style builders.

/**
 * A compilation of stroke styles to be reused.
 */
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