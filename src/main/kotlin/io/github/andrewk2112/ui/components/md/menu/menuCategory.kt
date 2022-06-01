package io.github.andrewk2112.ui.components.md.menu

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import kotlinx.css.*
import react.RBuilder
import react.dom.p

fun RBuilder.menuCategory(context: Context, categoryName: String) =
    p(MenuCategoryStyles.menuCategory(context).name) { +categoryName }

private object MenuCategoryStyles : DynamicStyleSheet() {

    val menuCategory by dynamicCss<Context> {
        marginTop    = StyleValues.spacing.absolute43
        marginLeft   = StyleValues.spacing.absolute24
        marginBottom = StyleValues.spacing.absolute15
        fontSize = StyleValues.fontSizes.relative1p1
        color = Theme.palette.onSurface2(it)
    }

}
