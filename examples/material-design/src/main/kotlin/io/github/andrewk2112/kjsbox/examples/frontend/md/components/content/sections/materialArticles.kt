package io.github.andrewk2112.kjsbox.examples.frontend.md.components.content.sections

import io.github.andrewk2112.kjsbox.frontend.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.designtokens.StyleValues
import io.github.andrewk2112.kjsbox.frontend.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.hooks.useAppContext
import io.github.andrewk2112.kjsbox.frontend.hooks.useLocalizator
import io.github.andrewk2112.kjsbox.frontend.localization.LocalizationKey
import io.github.andrewk2112.kjsbox.examples.frontend.md.components.common.images.strokedImage
import io.github.andrewk2112.kjsbox.examples.frontend.md.styles.*
import io.github.andrewk2112.kjsbox.examples.frontend.resourcewrappers.images.Image
import io.github.andrewk2112.kjsbox.examples.frontend.resourcewrappers.images.materialdesign.CustomColorPalettesGenerationImage
import io.github.andrewk2112.kjsbox.examples.frontend.resourcewrappers.images.materialdesign.ShapePowerImage
import io.github.andrewk2112.kjsbox.examples.frontend.resourcewrappers.images.materialdesign.SystemIconsImage
import io.github.andrewk2112.kjsbox.examples.frontend.resourcewrappers.images.materialdesign.TypeSystemImage
import io.github.andrewk2112.kjsbox.examples.frontend.resourcewrappers.locales.materialdesign.*
import io.github.andrewk2112.kjsbox.frontend.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.stylesheets.NamedRuleSet
import kotlinx.css.*
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.p



// Components.

val materialArticles = VFC {

    val context     = useAppContext()
    val localizator = useLocalizator()
    val uiState    by useState { MaterialArticlesUiState() }

    container {
        titleAndDescription(
            context,
            localizator(materialArticlesKey),
            localizator(makeProgressFasterWithTheseHelpfulArticlesKey)
        )
        articlesGrid(context) {
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

private inline fun ChildrenBuilder.container(crossinline children: ChildrenBuilder.() -> Unit) =
    +div(clazz = MaterialArticlesStyles.container.name, children)

private fun ChildrenBuilder.titleAndDescription(context: Context, title: String, description: String) {
    +h2(clazz = MaterialArticlesStyles.title(context).name) { +title }
    +p(clazz = MaterialArticlesStyles.description(context).name) { +description }
}

private inline fun ChildrenBuilder.articlesGrid(
    context: Context,
    crossinline adapter: ((isDouble: Boolean, title: String, desc: String, Image, imageAltText: String) -> Unit) -> Unit
) =
    +div(clazz = MaterialArticlesStyles.grid(context).name) {
        adapter { hasDoubleWidth, title, description, illustration, illustrationAlternativeText ->
            articleItem(context, hasDoubleWidth, title, description, illustration, illustrationAlternativeText)
        }
    }

private fun ChildrenBuilder.articleItem(
    context: Context,
    hasDoubleWidth: Boolean,
    title: String,
    description: String,
    illustration: Image,
    illustrationAlternativeText: String,
) =
    +div(clazz = LayoutStyles.run { if (hasDoubleWidth) gridDoubleItem else gridItem }(context).name) {
        +div(clazz = SelectionStyles.hoverableWithDefaultPaddedStroke(context).name) {
            +strokedImage(clazz = ImageStyles.fitWidthKeepAspectImage.name) {
                image           = illustration
                alternativeText = illustrationAlternativeText
            }
            +p(clazz = MaterialArticlesStyles.articleTitle(context).name) { +title }
            +p(clazz = MaterialArticlesStyles.articleDescription(context).name) { +description }
        }
    }



// Styles.

private object MaterialArticlesStyles : DynamicStyleSheet() {

    val container: NamedRuleSet by css {
        +LayoutStyles.contentContainer.rules
        padding(
            top        = StyleValues.spacing.absolute43,
            bottom     = StyleValues.spacing.absolute50,
            horizontal = StyleValues.spacing.absolute20
        )
    }

    val title: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockTitle(it).rules
        margin(horizontal = StyleValues.spacing.absolute20)
    }

    val description: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockDescription(it).rules
        margin(
            left  = StyleValues.spacing.absolute20,
            right = StyleValues.spacing.absolute20,
            top   = StyleValues.spacing.absolute19
        )
    }

    val grid: DynamicCssProvider<Context> by dynamicCss {
        +LayoutStyles.grid(it).rules
        padding(top = StyleValues.spacing.absolute26)
    }

    val articleTitle: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockSubTitle(it).rules
        marginTop = StyleValues.spacing.absolute24
    }

    val articleDescription: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockSmallerDescription(it).rules
        marginTop = StyleValues.spacing.absolute10
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
    val illustration: Image,
    val illustrationAlternativeText: LocalizationKey,
)
