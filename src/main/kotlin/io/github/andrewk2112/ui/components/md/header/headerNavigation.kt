package io.github.andrewk2112.ui.components.md.header

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.designtokens.stylesheets.DynamicCssProvider
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.designtokens.stylesheets.NamedRuleSet
import io.github.andrewk2112.extensions.AspectRatio
import io.github.andrewk2112.extensions.aspectRatio
import io.github.andrewk2112.extensions.withClassName
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.resources.iconMagnify
import io.github.andrewk2112.resources.iconMaterialDesignLogo
import io.github.andrewk2112.ui.styles.IconStyles
import io.github.andrewk2112.ui.styles.TransitionStyles
import kotlinx.css.*
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.header
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.ul

// Public.

val headerNavigation = FC<Props> {

    // Preparing the state.
    val context     = useAppContext()
    val localizator = useLocalizator()
    val data by useState { HeaderNavigationData() }

    withClassName(header, HeaderNavigationStyles.container(context).name) {

        // Logo block.
        withClassName(div, HeaderNavigationStyles.materialLogoBlock.name) {

            // Icon.
            withClassName(iconMaterialDesignLogo.component, HeaderNavigationStyles.materialDesignIcon.name) {}

            // Label.
            withClassName(span, HeaderNavigationStyles.materialDesignLabel(context).name) {
                +localizator("md.materialDesign").uppercase()
            }

        }

        // Navigation block.
        withClassName(div, HeaderNavigationStyles.navigationBlock.name) {

            // Navigation buttons block.
            withClassName(ul, HeaderNavigationStyles.navigationButtonsWrapper(context).name) {

                for (navigationButtonData in data.navigationButtons) {
                    val (localizationKey, isSelected) = navigationButtonData
                    navigationButton(context, localizator(localizationKey), isSelected)
                }

            }

            // Search button.
            withClassName(div, HeaderNavigationStyles.searchIconWrapper.name) {
                withClassName(iconMagnify.component, HeaderNavigationStyles.searchIcon(context).name) {}
            }

        }

    }

}



// Private - reusable views.

private fun ChildrenBuilder.navigationButton(context: Context, label: String, isSelected: Boolean) {

    withClassName(li, HeaderNavigationStyles.navigationButton(context).name) {

        // Label.
        withClassName(span, HeaderNavigationStyles.navigationButtonLabel.name) { +label }

        // Selection indicator.
        if (isSelected) {
            withClassName(div, HeaderNavigationStyles.navigationButtonSelectionIndicator(context).name) {}
        }

    }

}



// Private - styles.

private object HeaderNavigationStyles : DynamicStyleSheet() {

    val container: DynamicCssProvider<Context> by dynamicCss {
        display         = Display.flex
        justifyContent  = JustifyContent.spaceBetween
        height          = StyleValues.sizes.absolute72
        backgroundColor = Theme.palette.surface1(it)
    }

    val materialLogoBlock: NamedRuleSet by css {
        display    = Display.flex
        alignItems = Align.center
    }

    val materialDesignIcon: NamedRuleSet by css {
        +IconStyles.smallSizedIcon.rules
        marginLeft = StyleValues.spacing.absolute24
    }

    val materialDesignLabel: DynamicCssProvider<Context> by dynamicCss {
        marginLeft = StyleValues.spacing.absolute16
        fontSize   = StyleValues.fontSizes.relativep95
        color      = Theme.palette.onSurface1(it)
    }

    val navigationBlock: NamedRuleSet by css {
        display = Display.flex
        height  = 100.pct
    }

    val navigationButtonsWrapper: DynamicCssProvider<Context> by dynamicCss {
        display = Display.flex
        hover {
            descendants(".${navigationButton(it).name}:not(:hover)") {
                color = Theme.palette.onSurfaceDimmed1(it)
            }
        }
    }

    val navigationButton: DynamicCssProvider<Context> by dynamicCss {
        +TransitionStyles.defaultTransition(::color).rules
        display = Display.grid
        height  = 100.pct
        padding(horizontal = StyleValues.spacing.absolute16)
        gridTemplateRows = GridTemplateRows.repeat("3, 1fr")
        cursor = Cursor.pointer
        color = Theme.palette.onSurface1(it)
        hover {
            color = Theme.palette.onSurfaceFocused1(it)
            descendants(".${navigationButtonSelectionIndicator(it).name}") {
                backgroundColor = Theme.palette.onSurfaceFocused1(it)
            }
        }
    }

    val navigationButtonLabel: NamedRuleSet by css {
        gridRow   = GridRow("2")
        alignSelf = Align.center
    }

    val navigationButtonSelectionIndicator: DynamicCssProvider<Context> by dynamicCss {
        +TransitionStyles.defaultTransition(::backgroundColor).rules
        gridRow   = GridRow("3")
        alignSelf = Align.end
        width     = 100.pct
        height    = StyleValues.sizes.absolute4
        backgroundColor = Theme.palette.onSurface1(it)
    }

    val searchIconWrapper: NamedRuleSet by css {
        display     = Display.flex
        height      = 100.pct
        aspectRatio = AspectRatio(1)
        marginLeft  = StyleValues.spacing.absolute24
    }

    val searchIcon: DynamicCssProvider<Context> by dynamicCss {
        margin(LinearDimension.auto)
        color = Theme.palette.onSurface1(it)
    }

}



// Private - menu items structure.

private class HeaderNavigationData {

    val navigationButtons: Array<Pair<String, Boolean>> = arrayOf(
        Pair("md.design",     true),
        Pair("md.components", false),
        Pair("md.develop",    false),
        Pair("md.resources",  false),
        Pair("md.blog",       false),
    )

}
