@file:Suppress("FunctionName")

package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.menu

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.menu.MenuItemSpacingUiState.*
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.resources.endpoints.NavMenuMaterialEndpoints
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.LocalizationKey
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useLocalizator
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.useDesignTokensContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.surfaces.RippleSurface
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.useMaterialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component.BorderContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component.BorderContext.Position.BOTTOM
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component.BorderContext.Position.RIGHT
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.icons.materialdesign.MaterialDesignLogoIcon
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.COLOR_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.COMMUNICATION_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.ENVIRONMENT_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.FOUNDATION_OVERVIEW_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.GUIDELINES_OVERVIEW_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.ICONOGRAPHY_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.INTERACTION_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.INTRODUCTION_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.LAYOUT_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.MACHINE_LEARNING_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.MATERIAL_DESIGN_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.MATERIAL_FOUNDATION_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.MATERIAL_GUIDELINES_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.MATERIAL_STUDIES_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.MATERIAL_SYSTEM_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.MATERIAL_THEMING_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.MOTION_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.NAVIGATION_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.PLATFORM_GUIDANCE_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.SHAPE_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.SOUND_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.TYPOGRAPHY_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.USABILITY_KEY
import io.github.andrewk2112.utility.react.dom.extensions.safeBlankHref
import io.github.andrewk2112.utility.react.hooks.useMemoWithReferenceCount
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

val Menu = FC {

    val context              = useDesignTokensContext()
    val localizator          = useLocalizator()
    val component            = useMaterialDesignComponent()
    val materialDesignTokens = component.getMaterialDesignTokens()
    val styles               = useMemoWithReferenceCount(component) { MenuStyles(materialDesignTokens) }
    val uiState             by useState { MenuUiState(NavMenuMaterialEndpoints()) }

    Container(context, styles) {
        Header(context, styles, localizator(MATERIAL_DESIGN_KEY))
        Items(styles) {
            for ((categoryIndex, category) in uiState.categories.withIndex()) {
                Category(context, styles, localizator(category.name))
                for (item in category.items) {
                    Item(
                        context, styles, materialDesignTokens,
                        localizator(item.title), item.destinationEndpoint, item.bottomSpacing.getStyle(styles).name
                    )
                }
                if (categoryIndex != uiState.categories.lastIndex) {
                    Divider(context, styles)
                }
            }
        }
    }

}

private inline fun ChildrenBuilder.Container(
    context: Context,
    styles: MenuStyles,
    crossinline children: ChildrenBuilder.() -> Unit
) =
    +div(clazz = styles.container(context).name, children)

/**
 * A static header which stays at the top of the menu's layout all the time.
 */
private fun ChildrenBuilder.Header(context: Context, styles: MenuStyles, title: String) =
    +div(clazz = styles.header(context).name) {
        +MaterialDesignLogoIcon(clazz = styles.headerIcon.name)
        +span(clazz = styles.headerLabel(context).name) { +title.uppercase() }
    }

private inline fun ChildrenBuilder.Items(styles: MenuStyles, crossinline children: ChildrenBuilder.() -> Unit) =
    +div(clazz = styles.items.name, children)

private fun ChildrenBuilder.Category(context: Context, styles: MenuStyles, name: String) =
    +p(clazz = styles.category(context).name) { +name }

private fun ChildrenBuilder.Item(
    context: Context,
    styles: MenuStyles,
    materialDesignTokens: MaterialDesignTokens,
    name: String,
    destinationEndpoint: String,
    vararg classNames: String,
) =
    +RippleSurface(
        materialDesignTokens.component.selection.simpleHighlightingAndSelection(context).name,
        *classNames
    ) {
        +a(clazz = styles.itemLink(context).name) {
            safeBlankHref = destinationEndpoint
            +name
        }
    }

private fun ChildrenBuilder.Divider(context: Context, styles: MenuStyles) =
    +div(clazz = styles.divider(context).name)



// Styles.

private class MenuStyles(
    private val materialDesignTokens: MaterialDesignTokens
) : DynamicStyleSheet(materialDesignTokens::class) {

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

private fun MenuItemSpacingUiState.getStyle(styles: MenuStyles): NamedRuleSet =
    when (this) {
        REGULAR -> styles.itemBottomSpacingRegular
        BIG     -> styles.itemBottomSpacingBig
        MAX     -> styles.itemBottomSpacingMax
    }



// UI state.

private class MenuUiState private constructor(vararg val categories: CategoryUiState) {

    constructor(endpoints: NavMenuMaterialEndpoints) : this(
        CategoryUiState(
            MATERIAL_SYSTEM_KEY,
            MenuItemUiState(INTRODUCTION_KEY,     endpoints.introduction,            BIG),
            MenuItemUiState(MATERIAL_STUDIES_KEY, endpoints.aboutOurMaterialStudies, BIG),
        ),
        CategoryUiState(
            MATERIAL_FOUNDATION_KEY,
            MenuItemUiState(FOUNDATION_OVERVIEW_KEY, endpoints.foundationOverview,      BIG),
            MenuItemUiState(ENVIRONMENT_KEY,         endpoints.environmentSurfaces),
            MenuItemUiState(LAYOUT_KEY,              endpoints.understandingLayout),
            MenuItemUiState(NAVIGATION_KEY,          endpoints.understandingNavigation),
            MenuItemUiState(COLOR_KEY,               endpoints.colorSystem),
            MenuItemUiState(TYPOGRAPHY_KEY,          endpoints.typographySystem),
            MenuItemUiState(SOUND_KEY,               endpoints.aboutSound),
            MenuItemUiState(ICONOGRAPHY_KEY,         endpoints.productIconography),
            MenuItemUiState(SHAPE_KEY,               endpoints.aboutShape),
            MenuItemUiState(MOTION_KEY,              endpoints.understandingMotion),
            MenuItemUiState(INTERACTION_KEY,         endpoints.interactionGestures),
            MenuItemUiState(COMMUNICATION_KEY,       endpoints.confirmationAcknowledgement),
            MenuItemUiState(MACHINE_LEARNING_KEY,    endpoints.understandingMlPatterns, BIG),
        ),
        CategoryUiState(
            MATERIAL_GUIDELINES_KEY,
            MenuItemUiState(GUIDELINES_OVERVIEW_KEY, endpoints.guidelinesOverview,          BIG),
            MenuItemUiState(MATERIAL_THEMING_KEY,    endpoints.materialThemingOverview),
            MenuItemUiState(USABILITY_KEY,           endpoints.accessibility),
            MenuItemUiState(PLATFORM_GUIDANCE_KEY,   endpoints.platformGuidanceAndroidBars, MAX),
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
