package io.github.andrewk2112.md.components.menu

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.localization.LocalizationKey
import io.github.andrewk2112.md.components.menu.MenuItemSpacingUiState.*
import io.github.andrewk2112.md.resources.endpoints.NavMenuMaterialEndpoints
import io.github.andrewk2112.md.styles.*
import io.github.andrewk2112.md.styles.AnimationStyles.addTapHighlighting
import io.github.andrewk2112.md.styles.StrokePosition.BOTTOM
import io.github.andrewk2112.md.styles.StrokePosition.RIGHT
import io.github.andrewk2112.resourcewrappers.icons.materialdesign.materialDesignLogoIcon
import io.github.andrewk2112.utility.safeBlankHref
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.ChildrenBuilder
import react.VFC
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.span
import react.useState



// Components.

val menu = VFC {

    val context     = useAppContext()
    val localizator = useLocalizator()
    val uiState    by useState { MenuUiState(NavMenuMaterialEndpoints()) }

    container(context) {
        header(context, localizator("md.materialDesign"))
        items {
            for ((categoryIndex, category) in uiState.categories.withIndex()) {
                category(context, localizator(category.name))
                for (item in category.items) {
                    item(context, localizator(item.title), item.destinationEndpoint, item.bottomSpacing.style.name)
                }
                if (categoryIndex != uiState.categories.lastIndex) {
                    divider(context)
                }
            }
        }
    }

}

private inline fun ChildrenBuilder.container(context: Context, crossinline children: ChildrenBuilder.() -> Unit) =
    +div(MenuStyles.container(context).name, block = children)

/**
 * A static header which stays at the top of the menu's layout all the time.
 */
private fun ChildrenBuilder.header(context: Context, title: String) =
    +div(MenuStyles.header(context).name) {
        +materialDesignLogoIcon(MenuStyles.headerIcon.name)
        +span(MenuStyles.headerLabel(context).name) { +title.uppercase() }
    }

private inline fun ChildrenBuilder.items(crossinline children: ChildrenBuilder.() -> Unit) =
    +div(MenuStyles.items.name, block = children)

private fun ChildrenBuilder.category(context: Context, name: String) = +p(MenuStyles.category(context).name) { +name }

private fun ChildrenBuilder.item(
    context: Context,
    name: String,
    destinationEndpoint: String,
    vararg classNames: String,
) =
    +div(MenuStyles.item.name, SelectionStyles.simpleHighlightingAndSelection(context).name, *classNames) {
        +a(MenuStyles.itemLink(context).name) {
            safeBlankHref = destinationEndpoint
            addTapHighlighting()
            +name
        }
    }

private fun ChildrenBuilder.divider(context: Context) = +div(MenuStyles.divider(context).name)



// Styles.

private object MenuStyles : DynamicStyleSheet() {

    val container: DynamicCssProvider<Context> by dynamicCss {
        +StrokeStyles.borderStroke(StrokeConfigs(it, StrokeColor.Default, RIGHT)).rules
        position = Position.absolute
        inset(0.px)
        display       = Display.flex
        flexDirection = FlexDirection.column
        backgroundColor = Theme.palette.surface2(it)
    }

    val header: DynamicCssProvider<Context> by dynamicCss {
        +StrokeStyles.borderStroke(StrokeConfigs(it, StrokeColor.Default, BOTTOM)).rules
        flexShrink    = 0.0
        height        = StyleValues.sizes.absolute170
        display       = Display.flex
        alignItems    = Align.center
        flexDirection = FlexDirection.row
    }

    val headerIcon: NamedRuleSet by css {
        +ImageStyles.smallSizedIcon.rules
        marginLeft  = StyleValues.spacing.absolute24
        marginRight = StyleValues.spacing.absolute16
    }

    val headerLabel: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.mono.rules
        fontSize = StyleValues.fontSizes.relativep95
        color    = Theme.palette.onSurface2(it)
    }

    val items: NamedRuleSet by css {
        overflow = Overflow.scroll
    }

    val category: DynamicCssProvider<Context> by dynamicCss {
        marginTop    = StyleValues.spacing.absolute43
        marginLeft   = StyleValues.spacing.absolute24
        marginBottom = StyleValues.spacing.absolute15
        fontSize = StyleValues.fontSizes.relative1p1
        color = Theme.palette.onSurface2(it)
    }

    val item: NamedRuleSet by css {
        position = Position.relative
        overflow = Overflow.hidden // to prevent the animation from getting outside
    }

    val itemBottomSpacingRegular: NamedRuleSet by css {
        marginBottom = StyleValues.spacing.absolute2
    }

    val itemBottomSpacingBig: NamedRuleSet by css {
        marginBottom = StyleValues.spacing.absolute9
    }

    val itemBottomSpacingMax: NamedRuleSet by css {
        marginBottom = StyleValues.spacing.absolute40
    }

    val itemLink: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.light.rules
        position = Position.relative // or the animation will appear on top
        display  = Display.inlineBlock
        width    = 100.pct
        padding(
            horizontal = StyleValues.spacing.absolute24,
            vertical   = StyleValues.spacing.absolute12
        )
        fontSize       = StyleValues.fontSizes.relativep875
        textDecoration = TextDecoration.none
        color = Theme.palette.onSurfaceLighter2(it)
        hover {
            color = Theme.palette.onSelection1(it)
        }
    }

    val divider: DynamicCssProvider<Context> by dynamicCss {
        +StrokeStyles.borderStroke(StrokeConfigs(it, StrokeColor.Default, BOTTOM)).rules
        marginTop = StyleValues.spacing.absolute40
    }

}

private val MenuItemSpacingUiState.style: NamedRuleSet
    get() = when (this) {
        REGULAR -> MenuStyles.itemBottomSpacingRegular
        BIG     -> MenuStyles.itemBottomSpacingBig
        MAX     -> MenuStyles.itemBottomSpacingMax
    }



// UI state.

private class MenuUiState private constructor(vararg val categories: CategoryUiState) {

    constructor(endpoints: NavMenuMaterialEndpoints) : this(
        CategoryUiState(
            "md.materialSystem",
            MenuItemUiState("md.introduction",    endpoints.introduction,            BIG),
            MenuItemUiState("md.materialStudies", endpoints.aboutOurMaterialStudies, BIG),
        ),
        CategoryUiState(
            "md.materialFoundation",
            MenuItemUiState("md.foundationOverview", endpoints.foundationOverview,      BIG),
            MenuItemUiState("md.environment",        endpoints.environmentSurfaces),
            MenuItemUiState("md.layout",             endpoints.understandingLayout),
            MenuItemUiState("md.navigation",         endpoints.understandingNavigation),
            MenuItemUiState("md.color",              endpoints.colorSystem),
            MenuItemUiState("md.typography",         endpoints.typographySystem),
            MenuItemUiState("md.sound",              endpoints.aboutSound),
            MenuItemUiState("md.iconography",        endpoints.productIconography),
            MenuItemUiState("md.shape",              endpoints.aboutShape),
            MenuItemUiState("md.motion",             endpoints.understandingMotion),
            MenuItemUiState("md.interaction",        endpoints.interactionGestures),
            MenuItemUiState("md.communication",      endpoints.confirmationAcknowledgement),
            MenuItemUiState("md.machineLearning",    endpoints.understandingMlPatterns, BIG),
        ),
        CategoryUiState(
            "md.materialGuidelines",
            MenuItemUiState("md.guidelinesOverview", endpoints.guidelinesOverview,          BIG),
            MenuItemUiState("md.materialTheming",    endpoints.materialThemingOverview),
            MenuItemUiState("md.usability",          endpoints.accessibility),
            MenuItemUiState("md.platformGuidance",   endpoints.platformGuidanceAndroidBars, MAX),
        ),
    )

}

private class CategoryUiState(val name: LocalizationKey, vararg val items: MenuItemUiState)

private class MenuItemUiState(
    val title: LocalizationKey,
    val destinationEndpoint: String,
    val bottomSpacing: MenuItemSpacingUiState = REGULAR
)

private enum class MenuItemSpacingUiState { REGULAR, BIG, MAX }
