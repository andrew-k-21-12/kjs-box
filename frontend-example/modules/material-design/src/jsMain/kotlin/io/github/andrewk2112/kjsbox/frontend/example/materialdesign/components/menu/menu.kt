package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.menu

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.core.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.core.localization.LocalizationKey
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.menu.MenuItemSpacingUiState.*
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.resources.endpoints.NavMenuMaterialEndpoints
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.icons.materialdesign.materialDesignLogoIcon
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.*
import io.github.andrewk2112.kjsbox.frontend.core.utility.safeBlankHref
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useAppContext
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useLocalizator
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.surfaces.rippleSurface
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.accessors.materialDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component.BorderContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component.BorderContext.Position.BOTTOM
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component.BorderContext.Position.RIGHT
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.ChildrenBuilder
import react.FC
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.span
import react.useState



// Components.

val menu = FC {

    val context     = useAppContext()
    val localizator = useLocalizator()
    val uiState    by useState { MenuUiState(NavMenuMaterialEndpoints()) }

    container(context) {
        header(context, localizator(materialDesignKey))
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
    +div(clazz = MenuStyles.container(context).name, children)

/**
 * A static header which stays at the top of the menu's layout all the time.
 */
private fun ChildrenBuilder.header(context: Context, title: String) =
    +div(clazz = MenuStyles.header(context).name) {
        +materialDesignLogoIcon(clazz = MenuStyles.headerIcon.name)
        +span(clazz = MenuStyles.headerLabel(context).name) { +title.uppercase() }
    }

private inline fun ChildrenBuilder.items(crossinline children: ChildrenBuilder.() -> Unit) =
    +div(clazz = MenuStyles.items.name, children)

private fun ChildrenBuilder.category(context: Context, name: String) =
    +p(clazz = MenuStyles.category(context).name) { +name }

private fun ChildrenBuilder.item(
    context: Context,
    name: String,
    destinationEndpoint: String,
    vararg classNames: String,
) =
    +rippleSurface(
        materialDesignTokens.component.selection.simpleHighlightingAndSelection(context).name,
        *classNames
    ) {
        +a(clazz = MenuStyles.itemLink(context).name) {
            safeBlankHref = destinationEndpoint
            +name
        }
    }

private fun ChildrenBuilder.divider(context: Context) = +div(clazz = MenuStyles.divider(context).name)



// Styles.

private object MenuStyles : DynamicStyleSheet() {

    val container: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.stroke.darkBorderStroke(BorderContext(it, RIGHT)).rules
        position = Position.absolute
        inset    = Inset(0.px)
        display       = Display.flex
        flexDirection = FlexDirection.column
        backgroundColor = materialDesignTokens.system.palette.surface2(it)
    }

    val header: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.stroke.darkBorderStroke(BorderContext(it, BOTTOM)).rules
        flexShrink    = 0.0
        height        = materialDesignTokens.reference.sizes.absolute170
        display       = Display.flex
        alignItems    = Align.center
        flexDirection = FlexDirection.row
    }

    val headerIcon: NamedRuleSet by css {
        +materialDesignTokens.component.image.smallSizedIcon.rules
        marginLeft  = materialDesignTokens.reference.spacing.absolute24
        marginRight = materialDesignTokens.reference.spacing.absolute16
    }

    val headerLabel: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.system.fontStyles.mono(it).rules
        fontSize = materialDesignTokens.reference.fontSizes.relative0p95
        color    = materialDesignTokens.system.palette.onSurface2(it)
    }

    val items: NamedRuleSet by css {
        overflow = Overflow.scroll
    }

    val category: DynamicCssProvider<Context> by dynamicCss {
        marginTop    = materialDesignTokens.reference.spacing.absolute43
        marginLeft   = materialDesignTokens.reference.spacing.absolute24
        marginBottom = materialDesignTokens.reference.spacing.absolute15
        fontSize = materialDesignTokens.reference.fontSizes.relative1p1
        color = materialDesignTokens.system.palette.onSurface2(it)
    }

    val itemBottomSpacingRegular: NamedRuleSet by css {
        marginBottom = materialDesignTokens.reference.spacing.absolute2
    }

    val itemBottomSpacingBig: NamedRuleSet by css {
        marginBottom = materialDesignTokens.reference.spacing.absolute9
    }

    val itemBottomSpacingMax: NamedRuleSet by css {
        marginBottom = materialDesignTokens.reference.spacing.absolute40
    }

    val itemLink: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.system.fontStyles.light(it).rules
        position = Position.relative // or the animation will appear on top
        display  = Display.inlineBlock
        width    = 100.pct
        padding  = Padding(
            horizontal = materialDesignTokens.reference.spacing.absolute24,
            vertical   = materialDesignTokens.reference.spacing.absolute12
        )
        fontSize       = materialDesignTokens.reference.fontSizes.relative0p875
        textDecoration = TextDecoration.none
        color = materialDesignTokens.system.palette.onSurface2Lighter(it)
        hover {
            color = materialDesignTokens.system.palette.onSelection1(it)
        }
    }

    val divider: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.stroke.darkBorderStroke(BorderContext(it, BOTTOM)).rules
        marginTop = materialDesignTokens.reference.spacing.absolute40
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
            materialSystemKey,
            MenuItemUiState(introductionKey,    endpoints.introduction,            BIG),
            MenuItemUiState(materialStudiesKey, endpoints.aboutOurMaterialStudies, BIG),
        ),
        CategoryUiState(
            materialFoundationKey,
            MenuItemUiState(foundationOverviewKey, endpoints.foundationOverview,      BIG),
            MenuItemUiState(environmentKey,        endpoints.environmentSurfaces),
            MenuItemUiState(layoutKey,             endpoints.understandingLayout),
            MenuItemUiState(navigationKey,         endpoints.understandingNavigation),
            MenuItemUiState(colorKey,              endpoints.colorSystem),
            MenuItemUiState(typographyKey,         endpoints.typographySystem),
            MenuItemUiState(soundKey,              endpoints.aboutSound),
            MenuItemUiState(iconographyKey,        endpoints.productIconography),
            MenuItemUiState(shapeKey,              endpoints.aboutShape),
            MenuItemUiState(motionKey,             endpoints.understandingMotion),
            MenuItemUiState(interactionKey,        endpoints.interactionGestures),
            MenuItemUiState(communicationKey,      endpoints.confirmationAcknowledgement),
            MenuItemUiState(machineLearningKey,    endpoints.understandingMlPatterns, BIG),
        ),
        CategoryUiState(
            materialGuidelinesKey,
            MenuItemUiState(guidelinesOverviewKey, endpoints.guidelinesOverview,          BIG),
            MenuItemUiState(materialThemingKey,    endpoints.materialThemingOverview),
            MenuItemUiState(usabilityKey,          endpoints.accessibility),
            MenuItemUiState(platformGuidanceKey,   endpoints.platformGuidanceAndroidBars, MAX),
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
