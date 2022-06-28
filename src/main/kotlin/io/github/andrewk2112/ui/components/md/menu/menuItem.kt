package io.github.andrewk2112.ui.components.md.menu

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.ui.components.md.ripple
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.RBuilder
import react.dom.*
import react.dom.html.AnchorTarget

// Utility.

enum class MenuBottomSpacing(val value: LinearDimension) {
    SMALL(StyleValues.spacing.absolute2),
    MEDIUM(StyleValues.spacing.absolute9),
    BIG(StyleValues.spacing.absolute40)
}



// Public.

fun RBuilder.menuItem(context: Context, bottomSpacing: MenuBottomSpacing, itemName: String, itemDestination: String) {

    // Just a background with a ripple touch effect and bottom spacing.
    ripple(context, MenuItemStyles.wrapper(bottomSpacing).name) {

        // Item's link itself.
        a(classes = MenuItemStyles.link(context).name) {

            attrs {
                target = AnchorTarget._blank.toString()
                href   = itemDestination
            }

            +itemName

        }

    }

}



// Private.

private object MenuItemStyles : DynamicStyleSheet() {

    val wrapper by dynamicCss<MenuBottomSpacing> {
        marginBottom = it.value
    }

    val link by dynamicCss<Context> {
        display = Display.inlineBlock
        width   = 100.pct
        paddingLeft   = StyleValues.spacing.absolute24
        paddingRight  = StyleValues.spacing.absolute24
        paddingTop    = StyleValues.spacing.absolute12
        paddingBottom = StyleValues.spacing.absolute12
        fontSize       = StyleValues.fontSizes.relativep875
        textDecoration = TextDecoration.none
        color = Theme.palette.onSurfaceLighter2(it)
        hover {
            color           = Theme.palette.onSelection1(it)
            backgroundColor = Theme.palette.selection1(it)
        }
    }

}
