package io.github.andrewk2112.md.components.content

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.md.components.common.strokedImage
import io.github.andrewk2112.md.models.ArticleItem
import io.github.andrewk2112.md.styles.ImageStyles
import io.github.andrewk2112.md.styles.LabelStyles
import io.github.andrewk2112.md.styles.SelectionStyles
import io.github.andrewk2112.resources.images.Image
import io.github.andrewk2112.resources.images.MdCraneImage
import io.github.andrewk2112.resources.images.MdReplyImage
import io.github.andrewk2112.resources.images.MdShrineImage
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import kotlinx.css.*
import react.ChildrenBuilder
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.p
import react.useState

// Public.

val contentMaterialStudies = FC<Props> {

    val context     = useAppContext()
    val localizator = useLocalizator()
    val data by useState { ContentMaterialStudiesData() }

    // Dark container.
    +div(ContentMaterialStudiesStyles.container(context).name) {

        // Common spaces for the header block.
        +div(ContentMaterialStudiesStyles.header.name) {

            // Title.
            +h2(LabelStyles.contentBlockDarkTitle(context).name) { +localizator("md.materialStudies") }

            // Description.
            +p(ContentMaterialStudiesStyles.description(context).name) {
                +localizator("md.getInspiredByTheWaysMaterialAdapts")
            }

        }

        // All study items in the grid.
        +div(ContentMaterialStudiesStyles.itemsGrid.name) {
            for (studyItem in data.studyItems) {
                val studyItemTitle = localizator(studyItem.title)
                studyItem(
                    context,
                    studyItem.illustration,
                    studyItemTitle,
                    studyItemTitle,
                    localizator(studyItem.description)
                )
            }
        }

    }

}



// Private - reusable views.

private fun ChildrenBuilder.studyItem(
    context: Context,
    illustration: Image,
    illustrationAlternativeText: String,
    title: String,
    descriptionText: String
) {

    // To prevent the item from taking the entire height of the grid's row.
    div {

        // Block with a hover style.
        +div(SelectionStyles.hoverableWithIntensePaddedStroke(context).name) {

            // Illustration.
            +strokedImage(ImageStyles.fitWidthKeepAspectImage.name) {
                image           = illustration
                alternativeText = illustrationAlternativeText
            }

            // Title.
            +p(ContentMaterialStudiesStyles.itemTitle(context).name) { +title }

            // Description.
            +p(ContentMaterialStudiesStyles.itemDescription(context).name) { +descriptionText }

        }

    }

}



// Private - styles.

private object ContentMaterialStudiesStyles : DynamicStyleSheet() {

    val container: DynamicCssProvider<Context> by dynamicCss {
        backgroundColor = Theme.palette.surface1(it)
    }

    val header: NamedRuleSet by css {
        padding(
            top   = StyleValues.spacing.absolute42,
            left  = StyleValues.spacing.absolute40,
            right = StyleValues.spacing.absolute40
        )
    }

    val description: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockDarkDescription(it).rules
        marginTop = StyleValues.spacing.absolute20
    }

    val itemsGrid: NamedRuleSet by css {
        display             = Display.grid
        gridTemplateColumns = GridTemplateColumns.repeat("3, 1fr")
        padding(
            horizontal = StyleValues.spacing.absolute20,
            top        = StyleValues.spacing.absolute26,
            bottom     = StyleValues.spacing.absolute52
        )
    }

    val itemTitle: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockDarkSubTitle(it).rules
        marginTop = StyleValues.spacing.absolute24
    }

    val itemDescription: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockDarkSmallerDescription(it).rules
        marginTop = StyleValues.spacing.absolute10
    }

}



// Private - data.

private class ContentMaterialStudiesData {

    val studyItems: Array<ArticleItem> = arrayOf(
        ArticleItem("md.designForRetail",       "md.learnHowMaterialCanBeUsedInRetailProducts",           MdShrineImage),
        ArticleItem("md.travelTreatment",       "md.discoverTheWaysMaterialWasUsedToDesignAndBuildCrane", MdCraneImage),
        ArticleItem("md.craftingCommunication", "md.materialCanBeUsedInCommunicationAndPlanningProducts", MdReplyImage)
    )

}
