@file:Suppress("FunctionName")

package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.sections

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.images.materialdesign.CustomColorPalettesGenerationImage
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.images.materialdesign.ShapePowerImage
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.images.materialdesign.SystemIconsImage
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.images.materialdesign.TypeSystemImage
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.LocalizationKey
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useLocalizator
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.useDesignTokensContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.images.StrokedImage
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.useMaterialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.CRAFT_UNIQUE_COLOR_SCHEME_FOR_YOUR_BRAND_WITH_THIS_ONLINE_TOOL_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.GENERATE_CUSTOM_COLOR_PALETTES_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.HARNESS_THE_POWER_OF_SHAPE_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.MAKE_PROGRESS_FASTER_WITH_THESE_HELPFUL_ARTICLES_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.MATERIAL_ARTICLES_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.SHAPES_CAN_DIRECT_ATTENTION_IDENTIFY_COMPONENTS_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.SYSTEM_ICONS_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.SYSTEM_ICONS_SYMBOLIZE_COMMON_ACTIONS_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.THE_TYPE_SYSTEM_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.USE_TYPOGRAPHY_TO_PRESENT_YOUR_DESIGN_AND_CONTENT_KEY
import io.github.andrewk2112.kjsbox.frontend.image.resources.Image
import io.github.andrewk2112.utility.react.components.FC
import io.github.andrewk2112.utility.react.hooks.useMemoWithReferenceCount
import kotlinx.css.*
import react.ChildrenBuilder
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.p
import react.useState



// Components.

val MaterialArticles by FC {

    val context              = useDesignTokensContext()
    val localizator          = useLocalizator()
    val component            = useMaterialDesignComponent()
    val materialDesignTokens = component.getMaterialDesignTokens()
    val styles               = useMemoWithReferenceCount(component) { MaterialArticlesStyles(materialDesignTokens) }
    val uiState             by useState { MaterialArticlesUiState() }

    Container(styles) {
        TitleAndDescription(
            context,
            styles,
            localizator(MATERIAL_ARTICLES_KEY),
            localizator(MAKE_PROGRESS_FASTER_WITH_THESE_HELPFUL_ARTICLES_KEY)
        )
        ArticlesGrid(context, styles, materialDesignTokens) {
            for ((index, article) in uiState.articles.withIndex()) {
                it(
                    index % 2 == 0,
                    localizator(article.title),
                    localizator(article.description),
                    article.illustration,
                    localizator(article.illustrationAlternativeText),
                )
            }
        }
    }

}

private inline fun ChildrenBuilder.Container(
    styles: MaterialArticlesStyles,
    crossinline children: ChildrenBuilder.() -> Unit
) =
    +div(clazz = styles.container.name, children)

private fun ChildrenBuilder.TitleAndDescription(
    context: Context,
    styles: MaterialArticlesStyles,
    title: String,
    description: String
) {
    +h2(clazz = styles.title(context).name) { +title }
    +p(clazz = styles.description(context).name) { +description }
}

private inline fun ChildrenBuilder.ArticlesGrid(
    context: Context,
    styles: MaterialArticlesStyles,
    materialDesignTokens: MaterialDesignTokens,
    crossinline adapter: ((isDouble: Boolean, title: String, desc: String, Image, imageAltText: String) -> Unit) -> Unit
) =
    +div(clazz = styles.grid(context).name) {
        adapter { hasDoubleWidth, title, description, illustration, illustrationAlternativeText ->
            ArticleItem(
                context, styles, materialDesignTokens,
                hasDoubleWidth,
                title, description, illustration, illustrationAlternativeText
            )
        }
    }

private fun ChildrenBuilder.ArticleItem(
    context: Context,
    styles: MaterialArticlesStyles,
    materialDesignTokens: MaterialDesignTokens,
    hasDoubleWidth: Boolean,
    title: String,
    description: String,
    illustration: Image,
    illustrationAlternativeText: String,
) =
    +div(
        clazz = materialDesignTokens.component.layout.run {
            if (hasDoubleWidth) gridDoubleItem else gridItem
        }(context).name
    ) {
        +div(clazz = materialDesignTokens.component.selection.hoverableWithDefaultPaddedStroke(context).name) {
            +StrokedImage(clazz = materialDesignTokens.component.image.fitWidthKeepAspectImage.name) {
                image           = illustration
                alternativeText = illustrationAlternativeText
            }
            +p(clazz = styles.articleTitle(context).name) { +title }
            +p(clazz = styles.articleDescription(context).name) { +description }
        }
    }



// Styles.

private class MaterialArticlesStyles(
    private val materialDesignTokens: MaterialDesignTokens
) : DynamicStyleSheet(materialDesignTokens::class) {

    val container: NamedRuleSet by css {
        +materialDesignTokens.component.layout.contentContainer.rules
        padding = Padding(
            top        = materialDesignTokens.reference.spacing.absolute43,
            bottom     = materialDesignTokens.reference.spacing.absolute50,
            horizontal = materialDesignTokens.reference.spacing.absolute20
        )
    }

    val title: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.label.contentBlockTitle(it).rules
        margin = Margin(horizontal = materialDesignTokens.reference.spacing.absolute20)
    }

    val description: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.label.contentBlockDescription(it).rules
        margin = Margin(
            left  = materialDesignTokens.reference.spacing.absolute20,
            right = materialDesignTokens.reference.spacing.absolute20,
            top   = materialDesignTokens.reference.spacing.absolute19
        )
    }

    val grid: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.layout.grid(it).rules
        padding = Padding(top = materialDesignTokens.reference.spacing.absolute26)
    }

    val articleTitle: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.label.contentBlockSubTitle(it).rules
        marginTop = materialDesignTokens.reference.spacing.absolute24
    }

    val articleDescription: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.label.contentBlockSmallerDescription(it).rules
        marginTop = materialDesignTokens.reference.spacing.absolute10
    }

}



// UI state.

private class MaterialArticlesUiState private constructor(vararg val articles: MaterialArticleUiState) {

    constructor() : this(
        MaterialArticleUiState(
            SYSTEM_ICONS_KEY,
            SYSTEM_ICONS_SYMBOLIZE_COMMON_ACTIONS_KEY,
            SystemIconsImage,
            SYSTEM_ICONS_KEY,
        ),
        MaterialArticleUiState(
            GENERATE_CUSTOM_COLOR_PALETTES_KEY,
            CRAFT_UNIQUE_COLOR_SCHEME_FOR_YOUR_BRAND_WITH_THIS_ONLINE_TOOL_KEY,
            CustomColorPalettesGenerationImage,
            GENERATE_CUSTOM_COLOR_PALETTES_KEY,
        ),
        MaterialArticleUiState(
            THE_TYPE_SYSTEM_KEY,
            USE_TYPOGRAPHY_TO_PRESENT_YOUR_DESIGN_AND_CONTENT_KEY,
            TypeSystemImage,
            THE_TYPE_SYSTEM_KEY,
        ),
        MaterialArticleUiState(
            HARNESS_THE_POWER_OF_SHAPE_KEY,
            SHAPES_CAN_DIRECT_ATTENTION_IDENTIFY_COMPONENTS_KEY,
            ShapePowerImage,
            HARNESS_THE_POWER_OF_SHAPE_KEY,
        ),
    )

}

private class MaterialArticleUiState(
    val title: LocalizationKey,
    val description: LocalizationKey,
    val illustration: Image,
    val illustrationAlternativeText: LocalizationKey,
)
