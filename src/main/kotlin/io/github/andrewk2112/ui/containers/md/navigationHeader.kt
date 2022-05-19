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
import io.github.andrewk2112.jsmodules.svg.SvgFile
import io.github.andrewk2112.ui.styles.IconsStyles
import kotlinx.css.*
import react.Props
import react.RBuilder
import react.dom.*
import react.fc

// Public.

val navigationHeader = fc<Props> {

    val context     = useAppContext()
    val localizator = useLocalizator()

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
            ul(NavigationHeaderStyles.navigationButtonsWrapper.name) {

                navigationButton(context, localizator("md.design"),     true)
                navigationButton(context, localizator("md.components"), false)
                navigationButton(context, localizator("md.develop"),    false)
                navigationButton(context, localizator("md.resources"),  false)
                navigationButton(context, localizator("md.blog"),       false)

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
        +IconsStyles.smallSizedIcon.rules
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

    val navigationButtonsWrapper by css {
        display = Display.flex
    }

    val navigationButton by css {
        display = Display.grid
        height  = 100.pct
        padding(horizontal = StyleValues.spacing.absolute16)
        gridTemplateRows = GridTemplateRows.repeat("3, 1fr")
    }

    val navigationButtonLabel by dynamicCss<Context> {
        gridRow   = GridRow("2")
        alignSelf = Align.center
        color     = Theme.palette.onSurface1(it)
    }

    val navigationButtonSelectionIndicator by dynamicCss<Context> {
        gridRow         = GridRow("3")
        alignSelf       = Align.end
        width           = 100.pct
        height          = StyleValues.sizes.absolute4
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

private fun RBuilder.navigationButton(context: Context, label: String, isSelected: Boolean) {
    li(NavigationHeaderStyles.navigationButton.name) {
        span(NavigationHeaderStyles.navigationButtonLabel(context).name) { +label }
        if (isSelected) {
            div(NavigationHeaderStyles.navigationButtonSelectionIndicator(context).name) {}
        }
    }
}

@JsModule("./icons/material-design-logo.svg")
@JsNonModule
private external val materialDesignLogo: SvgFile

@JsModule("./icons/magnify.svg")
@JsNonModule
private external val iconMagnify: SvgFile
