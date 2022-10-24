package io.github.andrewk2112.md.components.menu

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.extensions.withClassName
import kotlinx.css.*
import react.ChildrenBuilder
import react.dom.html.ReactHTML.p

fun ChildrenBuilder.menuCategory(context: Context, categoryName: String) =
    withClassName(p, MenuCategoryStyles.menuCategory(context).name) { +categoryName }

private object MenuCategoryStyles : DynamicStyleSheet() {

    val menuCategory: DynamicCssProvider<Context> by dynamicCss {
        marginTop    = StyleValues.spacing.absolute43
        marginLeft   = StyleValues.spacing.absolute24
        marginBottom = StyleValues.spacing.absolute15
        fontSize = StyleValues.fontSizes.relative1p1
        color = Theme.palette.onSurface2(it)
    }

}
