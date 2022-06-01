package io.github.andrewk2112.ui.containers.md

import csstype.ClassName
import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.extensions.AspectRatio
import io.github.andrewk2112.extensions.aspectRatio
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.hooks.useStateGetterOnce
import io.github.andrewk2112.ui.iconMagnify
import io.github.andrewk2112.ui.materialDesignLogo
import io.github.andrewk2112.ui.styles.IconStyles
import io.github.andrewk2112.ui.styles.TransitionStyles
import kotlinx.css.*
import react.Props
import react.RBuilder
import react.dom.*
import react.fc

// Public.

val navigationHeader = fc<Props> {

    val context     = useAppContext()
    val localizator = useLocalizator()
    val data        = useStateGetterOnce { NavigationHeaderData() }

    header(NavigationHeaderStyles.container(context).name) {

        // Logo block.
        div(NavigationHeaderStyles.materialLogoBlock.name) {

            // Icon.
            materialDesignLogo.component {
                attrs.className = ClassName(NavigationHeaderStyles.materialDesignIcon.name)
            }

            // Label.
            span(NavigationHeaderStyles.materialDesignLabel(context).name) {
                +localizator("md.materialDesign").uppercase()
            }

        }

        // Navigation block.
        div(NavigationHeaderStyles.navigationBlock.name) {

            // Navigation buttons block.
            ul(NavigationHeaderStyles.navigationButtonsWrapper(context).name) {
                for (navigationButtonData in data.navigationButtons) {
                    val (localizationKey, isSelected) = navigationButtonData
                    navigationButton(context, localizator(localizationKey), isSelected)
                }
            }

            // Search button.
            div(NavigationHeaderStyles.searchIconWrapper.name) {
                iconMagnify.component {
                    attrs.className = ClassName(NavigationHeaderStyles.searchIcon(context).name)
                }
            }

        }

    }

}



// Private.

private object NavigationHeaderStyles : DynamicStyleSheet() {

    val container by dynamicCss<Context> {
        display         = Display.flex
        justifyContent  = JustifyContent.spaceBetween
        height          = StyleValues.sizes.absolute72
        backgroundColor = Theme.palette.surface1(it)
    }

    val materialLogoBlock by css {
        display    = Display.flex
        alignItems = Align.center
    }

    val materialDesignIcon by css {
        +IconStyles.smallSizedIcon.rules
        marginLeft = StyleValues.spacing.absolute24
    }

    val materialDesignLabel by dynamicCss<Context> {
        marginLeft = StyleValues.spacing.absolute16
        fontSize   = StyleValues.fontSizes.relativep95
        color      = Theme.palette.onSurface1(it)
    }

    val navigationBlock by css {
        display = Display.flex
        height  = 100.pct
    }

    val navigationButtonsWrapper by dynamicCss<Context> {
        display = Display.flex
        hover {
            descendants(".${navigationButton(it).name}:not(:hover)") {
                color = Theme.palette.onSurfaceDimmed1(it)
            }
        }
    }

    val navigationButton by dynamicCss<Context> {
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

    val navigationButtonLabel by css {
        gridRow   = GridRow("2")
        alignSelf = Align.center
    }

    val navigationButtonSelectionIndicator by dynamicCss<Context> {
        +TransitionStyles.defaultTransition(::backgroundColor).rules
        gridRow   = GridRow("3")
        alignSelf = Align.end
        width     = 100.pct
        height    = StyleValues.sizes.absolute4
        backgroundColor = Theme.palette.onSurface1(it)
    }

    val searchIconWrapper by css {
        display     = Display.flex
        height      = 100.pct
        aspectRatio = AspectRatio(1)
        marginLeft  = StyleValues.spacing.absolute24
    }

    val searchIcon by dynamicCss<Context> {
        margin(LinearDimension.auto)
        color = Theme.palette.onSurface1(it)
    }

}

private class NavigationHeaderData {

    val navigationButtons: List<Pair<String, Boolean>> = listOf(
        Pair("md.design",     true),
        Pair("md.components", false),
        Pair("md.develop",    false),
        Pair("md.resources",  false),
        Pair("md.blog",       false),
    )

}

private fun RBuilder.navigationButton(context: Context, label: String, isSelected: Boolean) {
    li(NavigationHeaderStyles.navigationButton(context).name) {
        span(NavigationHeaderStyles.navigationButtonLabel.name) { +label }
        if (isSelected) {
            div(NavigationHeaderStyles.navigationButtonSelectionIndicator(context).name) {}
        }
    }
}
