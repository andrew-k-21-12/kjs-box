package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.HasCssSuffix
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.accessors.MaterialDesignTokens
import io.github.andrewk2112.utility.string.extensions.capitalize
import io.github.andrewk2112.utility.string.formats.cases.CamelCase
import io.github.andrewk2112.utility.string.formats.cases.SnakeCase
import io.github.andrewk2112.utility.string.formats.changeFormat
import kotlinx.css.*
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty0



// Utility - stroke color.

/**
 * A preferred coloring mode for the stroke.
 */
sealed class StrokeColor {
    object Default : StrokeColor()
    object Intense : StrokeColor()
    class Custom(val colorReference: KProperty0<(Context) -> Color>) : StrokeColor()
}

/**
 * Loads a themed color for the corresponding [StrokeColor] type according to the provided [context].
 */
private fun StrokeColor.getThemedColor(context: Context): Color = when (this) {
    StrokeColor.Default   -> MaterialDesignTokens.system.palette.stroke1(context)
    StrokeColor.Intense   -> MaterialDesignTokens.system.palette.stroke2(context)
    is StrokeColor.Custom -> colorReference.get().invoke(context)
}



// Utility - stroke position.

/**
 * Denotes the position of a stroke.
 */
enum class StrokePosition { LEFT, TOP, RIGHT, BOTTOM }

/** Lookup for a matching border styling property. */
private val StrokePosition.matchingStylingProperty: KMutableProperty1<StyledElement, Border>
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
    vararg val positions: StrokePosition = StrokePosition.entries.toTypedArray(),
) : HasCssSuffix {

    override val cssSuffix: String
        get() = context.cssSuffix +
                color::class.simpleName + ((color as? StrokeColor.Custom)?.colorReference?.name?.capitalize() ?: "") +
                positions
                    .sorted()
                    .joinToString(separator = "") { it.name.changeFormat(SnakeCase, CamelCase) }

}



// Style builders.

/**
 * A compilation of stroke styles to be reused.
 */
object StrokeStyles : DynamicStyleSheet() {

    /** Applies a border-based stroke. */
    val borderStroke: DynamicCssProvider<StrokeConfigs> by dynamicCss {
        val strokeWidth = MaterialDesignTokens.system.sizes.stroke(it.context)
        val strokeColor = it.color.getThemedColor(it.context)
        it.positions.map { position ->
            position.matchingStylingProperty.set(this, Border(strokeWidth, BorderStyle.solid, strokeColor))
        }
    }

    /** Applies an outline-based stroke. */
    val outlineStroke: DynamicCssProvider<StrokeConfigs> by dynamicCss {
        outlineWidth = MaterialDesignTokens.system.sizes.stroke(it.context)
        outlineStyle = OutlineStyle.solid
        outlineColor = it.color.getThemedColor(it.context)
    }

}
