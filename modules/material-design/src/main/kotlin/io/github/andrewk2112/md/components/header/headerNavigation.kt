package io.github.andrewk2112.md.components.header

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.Context.ScreenSize.*
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.extensions.asMouseEventHandler
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.md.resources.iconMagnify
import io.github.andrewk2112.md.resources.iconMaterialDesignLogo
import io.github.andrewk2112.md.resources.iconMenu
import io.github.andrewk2112.md.styles.FontStyles
import io.github.andrewk2112.md.styles.ImageStyles
import io.github.andrewk2112.md.styles.TransitionStyles
import kotlinx.css.*
import kotlinx.css.properties.AspectRatio
import react.*
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.header
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.nav
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.ul

// Public.

val headerNavigation = FC<HeaderProps> { props ->

    // Preparing the state.
    val context     = useAppContext()
    val localizator = useLocalizator()
    val data by useState { HeaderNavigationData() }

    +header(HeaderNavigationStyles.container(context).name) {

        // Logo block.
        +div(HeaderNavigationStyles.materialLogoBlock(context).name) {

            // Menu button.
            +button(
                HeaderNavigationStyles.menuButtonPositioning(props.hasSlidingMenu).name,
                HeaderNavigationStyles.menuButtonAppearance(context).name
            ) {
                onClick = props.onMenuClick.asMouseEventHandler()
                +iconMenu.component(HeaderNavigationStyles.menuButtonIcon.name)
            }

            // Icon.
            +iconMaterialDesignLogo.component(HeaderNavigationStyles.materialDesignIcon(props.hasSlidingMenu).name)

            // Label.
            +span(HeaderNavigationStyles.materialDesignLabel(context).name) {
                +localizator("md.materialDesign").uppercase()
            }

        }

        // Navigation block.
        +nav(HeaderNavigationStyles.navigationBlock(context).name) {

            // Navigation buttons block.
            +ul(HeaderNavigationStyles.navigationButtonsWrapper(context).name) {

                for (navigationButtonData in data.navigationButtons) {
                    val (localizationKey, isSelected) = navigationButtonData
                    navigationButton(context, localizator(localizationKey), isSelected)
                }

            }

        }

        // Search button.
        +div(HeaderNavigationStyles.searchIconWrapper(context).name) {
            +iconMagnify.component(HeaderNavigationStyles.searchIcon(context).name)
        }

    }

}



// Private - reusable views.

private fun ChildrenBuilder.navigationButton(context: Context, label: String, isSelected: Boolean) {

    // Positioning inside the container's space.
    +li(HeaderNavigationStyles.navigationButtonPositioning.name) {

        // The navigation button itself.
        +div(HeaderNavigationStyles.navigationButton(context).name) {

            // Label.
            +span(HeaderNavigationStyles.navigationButtonLabel(context).name) { +label }

            // Selection indicator.
            if (isSelected) {
                +div(HeaderNavigationStyles.navigationButtonSelectionIndicator(context).name)
            }

        }

    }

}



// Private - styles.

private object HeaderNavigationStyles : DynamicStyleSheet() {

    val container: DynamicCssProvider<Context> by dynamicCss {
        display        = Display.flex
        justifyContent = JustifyContent.spaceBetween
        backgroundColor = Theme.palette.surface1(it)
        height = if (it.isNarrowHeader) StyleValues.sizes.absolute112 else StyleValues.sizes.absolute72
    }

    val materialLogoBlock: DynamicCssProvider<Context> by dynamicCss {
        display    = Display.flex
        alignItems = Align.center
        if (it.isNarrowHeader) {
            height = StyleValues.sizes.absolute72
        }
    }

    val menuButtonPositioning: DynamicCssProvider<Boolean> by dynamicCss {
        height      = 100.pct
        aspectRatio = AspectRatio(1)
        if (!it) {
            display = Display.none
        }
    }

    val menuButtonAppearance: DynamicCssProvider<Context> by dynamicCss {
        backgroundColor = StyleValues.palette.transparent
        color           = Theme.palette.onSurface1(it)
        borderStyle     = BorderStyle.none
        cursor          = Cursor.pointer
    }

    val menuButtonIcon: NamedRuleSet by css {
        display = Display.block
        margin(LinearDimension.auto)
    }

    val materialDesignIcon: DynamicCssProvider<Boolean> by dynamicCss {
        +ImageStyles.smallSizedIcon.rules
        flexShrink = .0
        if (!it) {
            marginLeft = StyleValues.spacing.absolute24
        }
    }

    val materialDesignLabel: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.boldMono.rules
        marginLeft = StyleValues.spacing.absolute16
        fontSize   = StyleValues.fontSizes.relativep95
        color      = Theme.palette.onSurface1(it)
        if (it.screenSize < Context.ScreenSize.DESKTOP) {
            display = Display.none
        }
    }

    val navigationBlock: DynamicCssProvider<Context> by dynamicCss {
        display = Display.flex
        if (!it.isNarrowHeader) {
            height     = 100.pct
            marginLeft = LinearDimension.auto
        } else {
            position = Position.absolute
            left     = 0.px
            right    = 0.px
            bottom   = 0.px
        }
    }

    val navigationButtonsWrapper: DynamicCssProvider<Context> by dynamicCss {
        display = Display.flex
        width   = 100.pct
        hover {
            descendants(".${navigationButton(it).name}:not(:hover)") {
                color = Theme.palette.onSurfaceDimmed1(it)
            }
        }
        if (it.isNarrowHeader) {
            height = StyleValues.sizes.absolute48
        }
    }

    val navigationButtonPositioning: NamedRuleSet by css {
        flexBasis = FlexBasis.zero // the initial main size of the item:
        flexGrow  = 1              // in a combination with this grow value makes all items' widths equal
        display   = Display.block
        textAlign = TextAlign.center // aligning the nested inline items
    }

    val navigationButton: DynamicCssProvider<Context> by dynamicCss {

        // Basic styling.
        +TransitionStyles.flashingTransition(::color).rules
        display = Display.inlineGrid // using the inline variant to fit to the contents' width
        height  = 100.pct
        gridTemplateRows = GridTemplateRows.repeat("3, 1fr")
        cursor = Cursor.pointer

        // Coloring with the hover logic.
        color = Theme.palette.onSurface1(it)
        hover {
            color = Theme.palette.onSurfaceFocused1(it)
            descendants(".${navigationButtonSelectionIndicator(it).name}") {
                backgroundColor = Theme.palette.onSurfaceFocused1(it)
            }
        }

        // Padding only for bigger screens.
        if (it.screenSize > PHONE) {
            padding(horizontal = StyleValues.spacing.absolute16)
        }

    }

    val navigationButtonLabel: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.light.rules
        gridRow   = GridRow("2")
        alignSelf = Align.center
        fontSize  = Theme.fontSizes.adaptive2(it)
    }

    val navigationButtonSelectionIndicator: DynamicCssProvider<Context> by dynamicCss {
        +TransitionStyles.flashingTransition(::backgroundColor).rules
        gridRow   = GridRow("3")
        alignSelf = Align.end
        width     = 100.pct
        height    = StyleValues.sizes.absolute4
        backgroundColor = Theme.palette.onSurface1(it)
    }

    val searchIconWrapper: DynamicCssProvider<Context> by dynamicCss {
        display     = Display.flex
        aspectRatio = AspectRatio(1)
        marginLeft  = StyleValues.spacing.absolute24
        height      = if (it.isNarrowHeader) StyleValues.sizes.absolute72 else 100.pct
    }

    val searchIcon: DynamicCssProvider<Context> by dynamicCss {
        margin(LinearDimension.auto)
        color = Theme.palette.onSurface1(it)
    }

}

private val Context.isNarrowHeader: Boolean get() = screenSize <= SMALL_TABLET



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
