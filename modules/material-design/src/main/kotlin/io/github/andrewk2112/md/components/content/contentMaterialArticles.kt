package io.github.andrewk2112.md.components.content

import io.github.andrewk2112.components.image
import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.md.models.ArticleItem
import io.github.andrewk2112.md.styles.*
import io.github.andrewk2112.resources.images.*
import io.github.andrewk2112.resources.images.Image
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import kotlinx.css.*
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.p

// Public.

val contentMaterialArticles = FC<Props> {

    // State.

    val context     = useAppContext()
    val localizator = useLocalizator()

    val data by useState { ContentMaterialArticlesData() }



    // Rendering.

    // Container with basic spacing.
    +div(ContentMaterialArticlesStyles.container.name) {

        // Title.
        +h2(ContentMaterialArticlesStyles.title(context).name) { +localizator("md.materialArticles") }

        // Description.
        +p(ContentMaterialArticlesStyles.description(context).name) {
            +localizator("md.makeProgressFasterWithTheseHelpfulArticles")
        }

        // Grid of the articles.
        +div(ContentMaterialArticlesStyles.gridContainer.name) {
            for (articleItem in data.articleItems) {
                val articleItemTitle = localizator(articleItem.title)
                articleBlock(
                    context,
                    articleItem.illustration,
                    articleItemTitle,
                    articleItemTitle,
                    localizator(articleItem.description)
                )
            }
        }

    }

}



// Private - reusable views.

private fun ChildrenBuilder.articleBlock(
    context: Context,
    illustration: Image,
    illustrationAlternativeText: String,
    title: String,
    descriptionText: String
) {

    // To prevent the item from taking the entire height of the grid's row.
    div {

        // Block with a hover style.
        +div(SelectionStyles.hoverableWithDefaultPaddedStroke(context).name) {

            // Wrapper to position the stroke inside the image's bounds, on top of it.
            +div(ContentMaterialArticlesStyles.strokedImageWrapper.name) {

                // Article's illustration.
                image(
                    illustration,
                    illustrationAlternativeText,
                    ContentMaterialArticlesStyles.widthRestrictedImage.name
                )

                // Inner semi-transparent image stroke.
                +div(ContentMaterialArticlesStyles.innerStrokeForImage(context).name)

            }

            // Title.
            +p(ContentMaterialArticlesStyles.articleTitle(context).name) { +title }

            // Description.
            +p(ContentMaterialArticlesStyles.articleDescription(context).name) { +descriptionText }

        }

    }

}



// Private - styles.

private object ContentMaterialArticlesStyles : DynamicStyleSheet() {

    val container: NamedRuleSet by css {
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

    val gridContainer: NamedRuleSet by css {
        display             = Display.grid
        gridTemplateColumns = GridTemplateColumns("2fr 1fr")
        rowGap              = StyleValues.spacing.absolute50
        padding(top = StyleValues.spacing.absolute26)
    }

    val strokedImageWrapper: NamedRuleSet by css {
        position = Position.relative
    }

    val widthRestrictedImage: NamedRuleSet by css {
        width  = 100.pct
        height = LinearDimension.auto
    }

    val innerStrokeForImage: DynamicCssProvider<Context> by dynamicCss {
        +StrokeStyles.borderStroke(StrokeConfigs(it, StrokeColor.INTENSE)).rules
        position = Position.absolute
        inset(0.px)
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



// Private - data.

private class ContentMaterialArticlesData {

    val articleItems: Array<ArticleItem> = arrayOf(
        ArticleItem(
            "md.systemIcons",
            "md.systemIconsSymbolizeCommonActions",
            MdSystemIconsImage
        ),
        ArticleItem(
            "md.generateCustomColorPalettes",
            "md.craftUniqueColorSchemeForYourBrandWithThisOnlineTool",
            MdCustomColorPalettesGenerationImage
        ),
        ArticleItem(
            "md.theTypeSystem",
            "md.useTypographyToPresentYourDesignAndContent",
            MdTypeSystemImage
        ),
        ArticleItem(
            "md.harnessThePowerOfShape",
            "md.shapesCanDirectAttentionIdentifyComponents",
            MdShapePowerImage
        ),
    )

}
