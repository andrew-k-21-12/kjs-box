package io.github.andrewk2112.md.components.menu

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import io.github.andrewk2112.extensions.withClassName
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.md.resources.endpoints.MaterialDesignTopicsEndpoints
import io.github.andrewk2112.md.resources.iconMaterialDesignLogo
import io.github.andrewk2112.md.styles.IconStyles
import io.github.andrewk2112.md.styles.StrokeColor.DEFAULT
import io.github.andrewk2112.md.styles.StrokeConfigs
import io.github.andrewk2112.md.styles.StrokePosition.BOTTOM
import io.github.andrewk2112.md.styles.StrokePosition.RIGHT
import io.github.andrewk2112.md.styles.StrokeStyles
import kotlinx.css.*
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span
import react.useState

// Public.

external interface MenuProps : Props {
    var headerHeight: Double
}

val menu = FC<MenuProps> { props ->

    val context     = useAppContext()
    val localizator = useLocalizator()
    val data by useState { MenuData() }

    // Topmost container.
    withClassName(div, MenuStyles.container(context).name) {

        // Menu header - lays below the sliding header.
        withClassName(
            div,
            MenuStyles.headerAppearance(context).name, MenuStyles.headerSize(props.headerHeight).name
        ) {

            // Material icon.
            withClassName(iconMaterialDesignLogo.component, MenuStyles.headerIcon.name) {}

            // Material label.
            withClassName(span, MenuStyles.headerLabel(context).name) {
                +localizator("md.materialDesign").uppercase()
            }

        }

        // Menu items.
        withClassName(div, MenuStyles.itemsContainer.name) {

            // Inserting each item according to the declared structure.
            for (item in data.menuItems) {
                when (item) {
                    is MenuItem.Category -> menuCategory(context, localizator(item.localizationKey))
                    is MenuItem.Item     -> menuItem(
                        context, item.bottomSpacing, localizator(item.localizationKey), item.destinationEndpoint
                    )
                    is MenuItem.Divider  -> menuDivider(context)
                }
            }

        }

    }

}



// Private - styles.

private object MenuStyles : DynamicStyleSheet() {

    val container: DynamicCssProvider<Context> by dynamicCss {
        +StrokeStyles.borderStroke(StrokeConfigs(it, DEFAULT, RIGHT)).rules
        flexShrink    = 0.0
        display       = Display.flex
        flexDirection = FlexDirection.column
        width = StyleValues.sizes.absolute280
        backgroundColor = Theme.palette.surface2(it)
    }

    val headerAppearance: DynamicCssProvider<Context> by dynamicCss {
        +StrokeStyles.borderStroke(StrokeConfigs(it, DEFAULT, BOTTOM)).rules
        flexShrink    = 0.0
        display       = Display.flex
        alignItems    = Align.center
        flexDirection = FlexDirection.row
    }

    val headerSize: DynamicCssProvider<Double> by dynamicCss { height = it.px }

    val headerIcon: NamedRuleSet by css {
        +IconStyles.smallSizedIcon.rules
        marginLeft  = StyleValues.spacing.absolute24
        marginRight = StyleValues.spacing.absolute16
    }

    val headerLabel: DynamicCssProvider<Context> by dynamicCss {
        fontSize = StyleValues.fontSizes.relativep95
        color    = Theme.palette.onSurface2(it)
    }

    val itemsContainer: NamedRuleSet by css {
        overflow = Overflow.scroll
    }

}



// Private - menu items structure.

private sealed class MenuItem {

    class Category(val localizationKey: String) : MenuItem()

    class Item(
        val localizationKey: String,
        val destinationEndpoint: String,
        val bottomSpacing: MenuItemBottomSpacing = MenuItemBottomSpacing.SMALL
    ) : MenuItem()

    object Divider : MenuItem()

}

private class MenuData {

    val menuItems: Array<MenuItem>

    init {
        val endpoints = MaterialDesignTopicsEndpoints()
        menuItems = arrayOf(
            MenuItem.Category("md.materialSystem"),
            MenuItem.Item("md.introduction", endpoints.introduction, MenuItemBottomSpacing.MEDIUM),
            MenuItem.Item("md.materialStudies", endpoints.aboutOurMaterialStudies, MenuItemBottomSpacing.MEDIUM),
            MenuItem.Divider,
            MenuItem.Category("md.materialFoundation"),
            MenuItem.Item("md.foundationOverview", endpoints.foundationOverview, MenuItemBottomSpacing.MEDIUM),
            MenuItem.Item("md.environment", endpoints.environmentSurfaces),
            MenuItem.Item("md.layout", endpoints.understandingLayout),
            MenuItem.Item("md.navigation", endpoints.understandingNavigation),
            MenuItem.Item("md.color", endpoints.colorSystem),
            MenuItem.Item("md.typography", endpoints.typographySystem),
            MenuItem.Item("md.sound", endpoints.aboutSound),
            MenuItem.Item("md.iconography", endpoints.productIconography),
            MenuItem.Item("md.shape", endpoints.aboutShape),
            MenuItem.Item("md.motion", endpoints.understandingMotion),
            MenuItem.Item("md.interaction", endpoints.interactionGestures),
            MenuItem.Item("md.communication", endpoints.confirmationAcknowledgement),
            MenuItem.Item("md.machineLearning", endpoints.understandingMLPatterns, MenuItemBottomSpacing.MEDIUM),
            MenuItem.Divider,
            MenuItem.Category("md.materialGuidelines"),
            MenuItem.Item("md.guidelinesOverview", endpoints.guidelinesOverview, MenuItemBottomSpacing.MEDIUM),
            MenuItem.Item("md.materialTheming", endpoints.materialThemingOverview),
            MenuItem.Item("md.usability", endpoints.accessibility),
            MenuItem.Item("md.platformGuidance", endpoints.platformGuidanceAndroidBars, MenuItemBottomSpacing.BIG)
        )
    }

}
