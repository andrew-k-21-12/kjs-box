package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.sections

import io.github.andrewk2112.kjsbox.frontend.core.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.hooks.useMemoWithReferenceCount
import io.github.andrewk2112.kjsbox.frontend.core.localization.LocalizationKey
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.images.strokedImage
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.images.materialdesign.CustomColorPalettesGenerationImage
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.images.materialdesign.ShapePowerImage
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.images.materialdesign.SystemIconsImage
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.images.materialdesign.TypeSystemImage
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.*
import io.github.andrewk2112.kjsbox.frontend.core.resources.Image as ResourceImage
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useLocalizator
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.useDesignTokensContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.useMaterialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import kotlinx.css.*
import react.ChildrenBuilder
import react.FC
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.p
import react.useState



// Components.

val materialArticles = FC {

    val context              = useDesignTokensContext()
    val localizator          = useLocalizator()
    val component            = useMaterialDesignComponent()
    val materialDesignTokens = component.getMaterialDesignTokens()
    val styles               = useMemoWithReferenceCount(component) { MaterialArticlesStyles(materialDesignTokens) }
    val uiState             by useState { MaterialArticlesUiState() }

    container(styles) {
        titleAndDescription(
            context,
            styles,
            localizator(materialArticlesKey),
            localizator(makeProgressFasterWithTheseHelpfulArticlesKey)
        )
        articlesGrid(context, styles, materialDesignTokens) {
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

private inline fun ChildrenBuilder.container(
    styles: MaterialArticlesStyles,
    crossinline children: ChildrenBuilder.() -> Unit
) =
    +div(clazz = styles.container.name, children)

private fun ChildrenBuilder.titleAndDescription(
    context: Context,
    styles: MaterialArticlesStyles,
    title: String,
    description: String
) {
    +h2(clazz = styles.title(context).name) { +title }
    +p(clazz = styles.description(context).name) { +description }
}

private inline fun ChildrenBuilder.articlesGrid(
    context: Context,
    styles: MaterialArticlesStyles,
    materialDesignTokens: MaterialDesignTokens,
    crossinline adapter: ((isDouble: Boolean, title: String, desc: String, ResourceImage, imageAltText: String) -> Unit) -> Unit
) =
    +div(clazz = styles.grid(context).name) {
        adapter { hasDoubleWidth, title, description, illustration, illustrationAlternativeText ->
            articleItem(
                context, styles, materialDesignTokens,
                hasDoubleWidth,
                title, description, illustration, illustrationAlternativeText
            )
        }
    }

private fun ChildrenBuilder.articleItem(
    context: Context,
    styles: MaterialArticlesStyles,
    materialDesignTokens: MaterialDesignTokens,
    hasDoubleWidth: Boolean,
    title: String,
    description: String,
    illustration: ResourceImage,
    illustrationAlternativeText: String,
) =
    +div(
        clazz = materialDesignTokens.component.layout.run {
            if (hasDoubleWidth) gridDoubleItem else gridItem
        }(context).name
    ) {
        +div(clazz = materialDesignTokens.component.selection.hoverableWithDefaultPaddedStroke(context).name) {
            +strokedImage(clazz = materialDesignTokens.component.image.fitWidthKeepAspectImage.name) {
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
            systemIconsKey,
            systemIconsSymbolizeCommonActionsKey,
            SystemIconsImage,
            systemIconsKey,
        ),
        MaterialArticleUiState(
            generateCustomColorPalettesKey,
            craftUniqueColorSchemeForYourBrandWithThisOnlineToolKey,
            CustomColorPalettesGenerationImage,
            generateCustomColorPalettesKey,
        ),
        MaterialArticleUiState(
            theTypeSystemKey,
            useTypographyToPresentYourDesignAndContentKey,
            TypeSystemImage,
            theTypeSystemKey,
        ),
        MaterialArticleUiState(
            harnessThePowerOfShapeKey,
            shapesCanDirectAttentionIdentifyComponentsKey,
            ShapePowerImage,
            harnessThePowerOfShapeKey,
        ),
    )

}

private class MaterialArticleUiState(
    val title: LocalizationKey,
    val description: LocalizationKey,
    val illustration: ResourceImage,
    val illustrationAlternativeText: LocalizationKey,
)
