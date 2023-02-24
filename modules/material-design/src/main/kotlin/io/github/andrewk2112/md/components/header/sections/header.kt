package io.github.andrewk2112.md.components.header.sections

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
import io.github.andrewk2112.localization.LocalizationKey
import io.github.andrewk2112.md.components.header.HeaderProps
import io.github.andrewk2112.md.styles.FontStyles
import io.github.andrewk2112.md.styles.ImageStyles
import io.github.andrewk2112.md.styles.TransitionStyles
import io.github.andrewk2112.resourcewrappers.icons.materialdesign.magnifyIcon
import io.github.andrewk2112.resourcewrappers.icons.materialdesign.materialDesignLogoIcon
import io.github.andrewk2112.resourcewrappers.icons.materialdesign.menuIcon
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



// Components.

val header = FC<HeaderProps> { props ->

    val context     = useAppContext()
    val localizator = useLocalizator()
    val uiState    by useState { HeaderUiState() }

    container(context) {
        logoBlock(context, props.hasCloseableMenu, localizator("md.materialDesign"), props.onMenuToggle)
        navigationBlock(context) {
            for (navigationItem in uiState.navigationItems) {
                it(localizator(navigationItem.title), navigationItem.isSelected)
            }
        }
        searchIcon(context)
    }

}

private inline fun ChildrenBuilder.container(context: Context, crossinline children: ChildrenBuilder.() -> Unit) =
    +header(HeaderStyles.container(context).name, block = children)

private fun ChildrenBuilder.logoBlock(
    context: Context,
    hasCloseableMenu: Boolean,
    label: String,
    onMenuButtonClick: () -> Unit
) =
    +div(HeaderStyles.logoBlock(context).name) {
        +button(
            HeaderStyles.menuButtonPositioning(hasCloseableMenu).name,
            HeaderStyles.menuButtonAppearance(context).name
        ) {
            onClick = onMenuButtonClick.asMouseEventHandler()
            +menuIcon(HeaderStyles.menuButtonIcon.name)
        }
        +materialDesignLogoIcon(HeaderStyles.logoIcon(hasCloseableMenu).name)
        +span(HeaderStyles.logoLabel(context).name) { +label.uppercase() }
    }

private inline fun ChildrenBuilder.navigationBlock(
    context: Context,
    crossinline navigationItemsAdapter: ((title: String, isSelected: Boolean) -> Unit) -> Unit
) =
    +nav(HeaderStyles.navigationBlock(context).name) {
        +ul(HeaderStyles.navigationItems(context).name) {
            navigationItemsAdapter { title, isSelected ->
                navigationItem(context, title, isSelected)
            }
        }
    }

private fun ChildrenBuilder.navigationItem(context: Context, title: String, isSelected: Boolean) =
    +li(HeaderStyles.navigationItemPositioning.name) {
        +div(HeaderStyles.navigationItem(context).name) {
            +span(HeaderStyles.navigationItemTitle(context).name) { +title }
            if (isSelected) {
                +div(HeaderStyles.navigationItemSelectionIndicator(context).name)
            }
        }
    }

private fun ChildrenBuilder.searchIcon(context: Context) =
    +div(HeaderStyles.searchIconWrapper(context).name) {
        +magnifyIcon(HeaderStyles.searchIcon(context).name)
    }



// Styles.

private object HeaderStyles : DynamicStyleSheet() {

    val container: DynamicCssProvider<Context> by dynamicCss {
        display        = Display.flex
        justifyContent = JustifyContent.spaceBetween
        height         = StyleValues.sizes.run { if (it.isNarrowHeader) absolute112 else absolute72 }
        backgroundColor = Theme.palette.surface1(it)
    }

    val logoBlock: DynamicCssProvider<Context> by dynamicCss {
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

    val logoIcon: DynamicCssProvider<Boolean> by dynamicCss {
        +ImageStyles.smallSizedIcon.rules
        flexShrink = 0
        if (!it) {
            marginLeft = StyleValues.spacing.absolute24
        }
    }

    val logoLabel: DynamicCssProvider<Context> by dynamicCss {
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

    val navigationItems: DynamicCssProvider<Context> by dynamicCss {
        display = Display.flex
        width   = 100.pct
        hover {
            descendants(".${navigationItem(it).name}:not(:hover)") {
                color = Theme.palette.onSurfaceDimmed1(it)
            }
        }
        if (it.isNarrowHeader) {
            height = StyleValues.sizes.absolute48
        }
    }

    val navigationItemPositioning: NamedRuleSet by css {
        flexBasis = FlexBasis.zero // the initial main size of the item:
        flexGrow  = 1              // in a combination with this grow value makes all items' widths equal
        display   = Display.block
        textAlign = TextAlign.center // aligning the nested inline items
    }

    val navigationItem: DynamicCssProvider<Context> by dynamicCss {

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
            descendants(".${navigationItemSelectionIndicator(it).name}") {
                backgroundColor = Theme.palette.onSurfaceFocused1(it)
            }
        }

        // Padding only for bigger screens.
        if (it.screenSize > PHONE) {
            padding(horizontal = StyleValues.spacing.absolute16)
        }

    }

    val navigationItemTitle: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.light.rules
        gridRow   = GridRow("2")
        alignSelf = Align.center
        fontSize  = Theme.fontSizes.adaptive2(it)
    }

    val navigationItemSelectionIndicator: DynamicCssProvider<Context> by dynamicCss {
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

private inline val Context.isNarrowHeader: Boolean get() = screenSize <= SMALL_TABLET



// UI state.

private class HeaderUiState private constructor(vararg val navigationItems: NavigationItemUiState) {

    constructor() : this(
        NavigationItemUiState("md.design",     true),
        NavigationItemUiState("md.components", false),
        NavigationItemUiState("md.develop",    false),
        NavigationItemUiState("md.resources",  false),
        NavigationItemUiState("md.blog",       false),
    )

}

private class NavigationItemUiState(val title: LocalizationKey, val isSelected: Boolean)
