package io.github.andrewk2112.md.components.menu

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.extensions.withClassName
import io.github.andrewk2112.md.components.ripple
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.ChildrenBuilder
import react.dom.html.AnchorTarget
import react.dom.html.ReactHTML.a

// Utility.

enum class MenuItemBottomSpacing(val value: LinearDimension) {
    SMALL(StyleValues.spacing.absolute2),
    MEDIUM(StyleValues.spacing.absolute9),
    BIG(StyleValues.spacing.absolute40)
}



// Public.

fun ChildrenBuilder.menuItem(
    context: Context,
    bottomSpacing: MenuItemBottomSpacing,
    name: String,
    destinationEndpoint: String
) {

    // Just a background with a ripple touch effect and bottom spacing.
    withClassName(ripple, MenuItemStyles.wrapper(bottomSpacing).name) {

        // Item's link itself.
        withClassName(a, MenuItemStyles.link(context).name) {

            target = AnchorTarget._blank
            href   = destinationEndpoint

            +name

        }

    }

}



// Private.

private object MenuItemStyles : DynamicStyleSheet() {

    val wrapper: DynamicCssProvider<MenuItemBottomSpacing> by dynamicCss {
        marginBottom = it.value
    }

    val link: DynamicCssProvider<Context> by dynamicCss {
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
