package io.github.andrewk2112.md.components.menu

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.extensions.withClassName
import io.github.andrewk2112.md.styles.AnimationStyles.addTapHighlighting
import io.github.andrewk2112.md.styles.SelectionStyles
import io.github.andrewk2112.utility.safeBlankHref
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.ChildrenBuilder
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div

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

    // Wrapper with the bottom spacing and highlighting.
    withClassName(
        div,
        MenuItemStyles.wrapperWithPositioning(bottomSpacing).name,
        SelectionStyles.simpleHighlightingAndSelection(context).name
    ) {

        // Item's link itself.
        withClassName(a, MenuItemStyles.link(context).name) {
            safeBlankHref = destinationEndpoint
            addTapHighlighting()
            +name
        }

    }

}



// Private.

private object MenuItemStyles : DynamicStyleSheet() {

    val wrapperWithPositioning: DynamicCssProvider<MenuItemBottomSpacing> by dynamicCss {
        position = Position.relative
        marginBottom = it.value
        overflow = Overflow.hidden // to prevent the animation from getting outside
    }

    val link: DynamicCssProvider<Context> by dynamicCss {
        position = Position.relative // or the animation will appear on top
        display  = Display.inlineBlock
        width    = 100.pct
        paddingLeft   = StyleValues.spacing.absolute24
        paddingRight  = StyleValues.spacing.absolute24
        paddingTop    = StyleValues.spacing.absolute12
        paddingBottom = StyleValues.spacing.absolute12
        fontSize       = StyleValues.fontSizes.relativep875
        textDecoration = TextDecoration.none
        color = Theme.palette.onSurfaceLighter2(it)
        hover {
            color = Theme.palette.onSelection1(it)
        }
    }

}
