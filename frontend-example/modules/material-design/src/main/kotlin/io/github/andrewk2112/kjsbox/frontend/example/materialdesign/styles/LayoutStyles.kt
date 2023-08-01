package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles

import io.github.andrewk2112.kjsbox.frontend.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.designtokens.Context.ScreenSize.PHONE
import io.github.andrewk2112.kjsbox.frontend.designtokens.Context.ScreenSize.SMALL_TABLET
import io.github.andrewk2112.kjsbox.frontend.designtokens.StyleValues
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles.GridColumns.*
import io.github.andrewk2112.kjsbox.frontend.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.stylesheets.NamedRuleSet
import kotlinx.css.*



// Public.

/**
 * Reusable styles for various types of layouts.
 */
object LayoutStyles : DynamicStyleSheet() {

    val contentContainer: NamedRuleSet by css {
        maxWidth = StyleValues.sizes.absolute1240
        margin(horizontal = LinearDimension.auto)
    }

    val grid: DynamicCssProvider<Context> by dynamicCss {
        val gridColumns = it.gridColumns
        display             = Display.grid
        gridTemplateColumns = GridTemplateColumns.repeat("${gridColumns.columnCount}, 1fr")
        rowGap              = StyleValues.spacing.run { if (gridColumns == TWO) absolute66 else absolute50 }
    }

    val gridItem: DynamicCssProvider<Context> by dynamicCss {}

    val gridDoubleItem: DynamicCssProvider<Context> by dynamicCss {
        if (it.gridColumns == THREE) {
            gridColumnEnd = GridColumnEnd("span 2")
        }
    }

    val gridDoubleOccupyingItem: DynamicCssProvider<Context> by dynamicCss {
        if (it.gridColumns != ONE) {
            gridColumnEnd = GridColumnEnd("span 2")
        }
    }

    val gridHidingItem: DynamicCssProvider<Context> by dynamicCss {
        if (it.gridColumns != THREE) {
            display = Display.none
        }
    }

}



// Private.

private enum class GridColumns(val columnCount: Int) { ONE(1), TWO(2), THREE(3) }

private val Context.gridColumns: GridColumns
    get() = when {
        screenSize <= PHONE        -> ONE
        screenSize <= SMALL_TABLET -> TWO
        else                       -> THREE
    }
