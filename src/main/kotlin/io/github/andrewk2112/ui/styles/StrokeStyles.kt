package io.github.andrewk2112.ui.styles

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.designtokens.stylesheets.DynamicCssProvider
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.designtokens.stylesheets.HasCssSuffix
import io.github.andrewk2112.extensions.camelCaseWord
import kotlinx.css.*
import kotlinx.css.properties.*

/**
 * Denotes the position of a stroke.
 * */
enum class StrokePosition { LEFT, TOP, RIGHT, BOTTOM }

/**
 * All configs required to evaluate a stroke's style.
 * */
class StrokeConfigs(val context: Context, vararg val positions: StrokePosition) : HasCssSuffix {

    override val cssSuffix: String
        get() = context.cssSuffix +
                positions
                    .sorted()
                    .joinToString(separator = "") { it.name.camelCaseWord() }

}

/**
 * A compilation of stroke styles to be reused.
 * */
object StrokeStyles : DynamicStyleSheet() {

    val defaultStroke: DynamicCssProvider<StrokeConfigs> by dynamicCss {
        val strokeWidth = StyleValues.sizes.absolute1
        val strokeColor = Theme.palette.stroke(it.context)
        it.positions.map { position ->
            position.matchingStylingFunction.invoke(this, strokeWidth, BorderStyle.solid, strokeColor)
        }
    }

    private val StrokePosition.matchingStylingFunction: StyledElement.(LinearDimension, BorderStyle, Color) -> Unit
        get() = when (this) {
            StrokePosition.LEFT   -> StyledElement::borderLeft
            StrokePosition.TOP    -> StyledElement::borderTop
            StrokePosition.RIGHT  -> StyledElement::borderRight
            StrokePosition.BOTTOM -> StyledElement::borderBottom
        }

}
