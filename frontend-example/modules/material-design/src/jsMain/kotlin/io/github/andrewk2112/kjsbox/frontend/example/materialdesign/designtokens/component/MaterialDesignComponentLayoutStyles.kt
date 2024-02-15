package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context.ScreenSize.PHONE
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context.ScreenSize.SMALL_TABLET
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component.GridColumns.*
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.reference.ReferenceSizes
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.reference.ReferenceSpacing
import kotlinx.css.*



// Public.

/**
 * Reusable layout styles.
 */
class MaterialDesignComponentLayoutStyles(
    private val referenceSizes: ReferenceSizes,
    private val referenceSpacing: ReferenceSpacing,
) : DynamicStyleSheet() {

    val contentContainer: NamedRuleSet by css {
        maxWidth = referenceSizes.absolute1240
        margin   = Margin(horizontal = LinearDimension.auto)
    }

    val grid: DynamicCssProvider<Context> by dynamicCss {
        val gridColumns = it.gridColumns
        display             = Display.grid
        gridTemplateColumns = GridTemplateColumns.repeat("${gridColumns.columnCount}, 1fr")
        rowGap              = referenceSpacing.run {
                                  if (gridColumns == TWO) absolute66 else absolute50
                              }
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
