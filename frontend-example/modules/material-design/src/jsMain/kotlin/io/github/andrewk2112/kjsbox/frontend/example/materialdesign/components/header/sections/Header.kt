@file:Suppress("FunctionName")

package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.header.sections

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.LocalizationKey
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useLocalizator
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context.ScreenSize.PHONE
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context.ScreenSize.SMALL_TABLET
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.useDesignTokensContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.header.HeaderProps
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.useMaterialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.icons.materialdesign.MagnifyIcon
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.icons.materialdesign.MaterialDesignLogoIcon
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.icons.materialdesign.MenuIcon
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.BLOG_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.COMPONENTS_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.DESIGN_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.DEVELOP_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.MATERIAL_DESIGN_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.RESOURCES_KEY
import io.github.andrewk2112.utility.react.components.FC
import io.github.andrewk2112.utility.react.dom.extensions.asEventHandler
import io.github.andrewk2112.utility.react.hooks.useMemoWithReferenceCount
import kotlinx.css.*
import kotlinx.css.properties.AspectRatio
import react.ChildrenBuilder
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.header
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.nav
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.ul
import react.useState



// Components.

val Header by FC<HeaderProps> { props ->

    val context     = useDesignTokensContext()
    val localizator = useLocalizator()
    val component   = useMaterialDesignComponent()
    val styles      = useMemoWithReferenceCount(component) { HeaderStyles(component.getMaterialDesignTokens()) }
    val uiState    by useState { HeaderUiState() }

    Container(context, styles) {
        LogoBlock(context, styles, props.hasCloseableMenu, localizator(MATERIAL_DESIGN_KEY), props.onMenuToggle)
        NavigationBlock(context, styles) {
            for (navigationItem in uiState.navigationItems) {
                it(localizator(navigationItem.title), navigationItem.isSelected)
            }
        }
        SearchIcon(context, styles)
    }

}

private inline fun ChildrenBuilder.Container(
    context: Context,
    styles: HeaderStyles,
    crossinline children: ChildrenBuilder.() -> Unit
) =
    +header(clazz = styles.container(context).name, children)

private fun ChildrenBuilder.LogoBlock(
    context: Context,
    styles: HeaderStyles,
    hasCloseableMenu: Boolean,
    label: String,
    onMenuButtonClick: () -> Unit
) =
    +div(clazz = styles.logoBlock(context).name) {
        +button(
            styles.menuButtonPositioning(hasCloseableMenu).name,
            styles.menuButtonAppearance(context).name
        ) {
            onClick = onMenuButtonClick.asEventHandler()
            +MenuIcon(clazz = styles.menuButtonIcon.name)
        }
        +MaterialDesignLogoIcon(clazz = styles.logoIcon(hasCloseableMenu).name)
        +span(clazz = styles.logoLabel(context).name) { +label.uppercase() }
    }

private inline fun ChildrenBuilder.NavigationBlock(
    context: Context,
    styles: HeaderStyles,
    crossinline navigationItemsAdapter: ((title: String, isSelected: Boolean) -> Unit) -> Unit
) =
    +nav(clazz = styles.navigationBlock(context).name) {
        +ul(clazz = styles.navigationItems(context).name) {
            navigationItemsAdapter { title, isSelected ->
                NavigationItem(context, styles, title, isSelected)
            }
        }
    }

private fun ChildrenBuilder.NavigationItem(context: Context, styles: HeaderStyles, title: String, isSelected: Boolean) =
    +li(clazz = styles.navigationItemPositioning.name) {
        +div(clazz = styles.navigationItem(context).name) {
            +span(clazz = styles.navigationItemTitle(context).name) { +title }
            if (isSelected) {
                +div(clazz = styles.navigationItemSelectionIndicator(context).name)
            }
        }
    }

private fun ChildrenBuilder.SearchIcon(context: Context, styles: HeaderStyles) =
    +div(clazz = styles.searchIconWrapper(context).name) {
        +MagnifyIcon(clazz = styles.searchIcon(context).name)
    }



// Styles.

private class HeaderStyles(
    private val materialDesignTokens: MaterialDesignTokens
) : DynamicStyleSheet(materialDesignTokens::class) {

    val container: DynamicCssProvider<Context> by dynamicCss {
        display        = Display.flex
        justifyContent = JustifyContent.spaceBetween
        height         = materialDesignTokens.reference.sizes.run { if (it.isNarrowHeader) absolute112 else absolute72 }
        backgroundColor = materialDesignTokens.system.palette.surface1(it)
    }

    val logoBlock: DynamicCssProvider<Context> by dynamicCss {
        display    = Display.flex
        alignItems = Align.center
        if (it.isNarrowHeader) {
            height = materialDesignTokens.reference.sizes.absolute72
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
        backgroundColor = materialDesignTokens.reference.palette.transparent
        color           = materialDesignTokens.system.palette.onSurface1(it)
        borderStyle     = BorderStyle.none
        cursor          = Cursor.pointer
    }

    val menuButtonIcon: NamedRuleSet by css {
        display = Display.block
        margin = Margin(LinearDimension.auto)
    }

    val logoIcon: DynamicCssProvider<Boolean> by dynamicCss {
        +materialDesignTokens.component.image.smallSizedIcon.rules
        flexShrink = 0
        if (!it) {
            marginLeft = materialDesignTokens.reference.spacing.absolute24
        }
    }

    val logoLabel: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.system.fontStyles.monoBold(it).rules
        marginLeft = materialDesignTokens.reference.spacing.absolute16
        fontSize   = materialDesignTokens.reference.fontSizes.relative0p95
        color      = materialDesignTokens.system.palette.onSurface1(it)
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
                color = materialDesignTokens.system.palette.onSurface1Dimmed(it)
            }
        }
        if (it.isNarrowHeader) {
            height = materialDesignTokens.reference.sizes.absolute48
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
        +materialDesignTokens.component.transition.flashing(::color).rules
        display = Display.inlineGrid // using the inline variant to fit to the contents' width
        height  = 100.pct
        gridTemplateRows = GridTemplateRows.repeat("3, 1fr")
        cursor = Cursor.pointer

        // Coloring with the hover logic.
        color = materialDesignTokens.system.palette.onSurface1(it)
        hover {
            color = materialDesignTokens.system.palette.onSurface1Focused(it)
            descendants(".${navigationItemSelectionIndicator(it).name}") {
                backgroundColor = materialDesignTokens.system.palette.onSurface1Focused(it)
            }
        }

        // Padding only for bigger screens.
        if (it.screenSize > PHONE) {
            padding = Padding(horizontal = materialDesignTokens.reference.spacing.absolute16)
        }

    }

    val navigationItemTitle: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.system.fontStyles.light(it).rules
        gridRow   = GridRow("2")
        alignSelf = Align.center
        fontSize  = materialDesignTokens.system.fontSizes.adaptive2(it)
    }

    val navigationItemSelectionIndicator: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.transition.flashing(::backgroundColor).rules
        gridRow   = GridRow("3")
        alignSelf = Align.end
        width     = 100.pct
        height    = materialDesignTokens.reference.sizes.absolute4
        backgroundColor = materialDesignTokens.system.palette.onSurface1(it)
    }

    val searchIconWrapper: DynamicCssProvider<Context> by dynamicCss {
        display     = Display.flex
        aspectRatio = AspectRatio(1)
        marginLeft  = materialDesignTokens.reference.spacing.absolute24
        height      = if (it.isNarrowHeader) materialDesignTokens.reference.sizes.absolute72 else 100.pct
    }

    val searchIcon: DynamicCssProvider<Context> by dynamicCss {
        margin = Margin(LinearDimension.auto)
        color = materialDesignTokens.system.palette.onSurface1(it)
    }

}

private inline val Context.isNarrowHeader: Boolean get() = screenSize <= SMALL_TABLET



// UI state.

private class HeaderUiState private constructor(vararg val navigationItems: NavigationItemUiState) {

    constructor() : this(
        NavigationItemUiState(DESIGN_KEY,     true),
        NavigationItemUiState(COMPONENTS_KEY, false),
        NavigationItemUiState(DEVELOP_KEY,    false),
        NavigationItemUiState(RESOURCES_KEY,  false),
        NavigationItemUiState(BLOG_KEY,       false),
    )

}

private class NavigationItemUiState(val title: LocalizationKey, val isSelected: Boolean)
