package io.github.andrewk2112.md.components.content

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useCurrentLanguageAndLocalizator
import io.github.andrewk2112.md.components.common.rectButton
import io.github.andrewk2112.md.models.BlogItem
import io.github.andrewk2112.md.resources.endpoints.WhatsNewMaterialEndpoints
import io.github.andrewk2112.md.styles.LabelStyles
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import io.github.andrewk2112.utility.DateFormat
import io.github.andrewk2112.utility.openBlankWindowSafely
import io.github.andrewk2112.utility.safeBlankHref
import kotlinx.css.*
import react.ChildrenBuilder
import react.FC
import react.Props
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.useState
import kotlin.js.Date

// Public.

val contentWhatsNew = FC<Props> {

    // State.

    val context                     = useAppContext()
    val (language, localizator)     = useCurrentLanguageAndLocalizator()
    val (dateFormat, formatOptions) = useState { Pair(DateFormat(), DateFormat.Options.LongDate()) }.component1()

    val endpoints by useState { WhatsNewMaterialEndpoints() }
    val data      by useState { ContentWhatsNewData(endpoints) }



    // Rendering.

    // Common spacing.
    +div(ContentWhatsNewStyles.container.name) {

        // Section's title.
        +a(LabelStyles.contentBlockLinkTitle(context).name) {
            safeBlankHref = endpoints.whatsNew
            +localizator("md.whatsNew")
        }

        // Section's short description.
        +p(ContentWhatsNewStyles.titleDescriptionWithSpacing(context).name) {
            +localizator("md.theLatestMaterialDesignUpdatesAndGuidance")
        }

        // Rendering some of the newest articles.
        for (blogItem in data.blogItems) {
            blogItemRow(
                context,
                localizator(blogItem.title),
                localizator(blogItem.description),
                dateFormat.format(blogItem.date, language, formatOptions).uppercase(),
                blogItem.endpoint
            )
        }

        // Button to view all articles.
        +rectButton(ContentWhatsNewStyles.viewAllButton.name) {
            text   = localizator("md.viewAll")
            action = { openBlankWindowSafely(endpoints.whatsNew) }
        }

    }

}



// Private - reusable view.

private fun ChildrenBuilder.blogItemRow(
    context: Context,
    title: String,
    description: String,
    formattedDate: String,
    destinationEndpoint: String
) {

    // Date stamp.
    +p(ContentWhatsNewStyles.blogItemDateWithSpacing(context).name) { +formattedDate }

    // Clickable title.
    +a(ContentWhatsNewStyles.blogItemTitleWithSpacing(context).name) {
        safeBlankHref = destinationEndpoint
        +title
    }

    // Short description.
    +p(ContentWhatsNewStyles.blogItemDescriptionWithSpacing(context).name) { +description }

}



// Private - styles.

private object ContentWhatsNewStyles : DynamicStyleSheet() {

    val container: NamedRuleSet by css {
        padding(
            top        = StyleValues.spacing.absolute89,
            bottom     = StyleValues.spacing.absolute47,
            horizontal = StyleValues.spacing.absolute40
        )
    }

    val titleDescriptionWithSpacing: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockDescription(it).rules
        marginTop    = StyleValues.spacing.absolute12
        marginBottom = StyleValues.spacing.absolute38
    }

    val blogItemDateWithSpacing: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockDate(it).rules
        marginTop = StyleValues.spacing.absolute27
    }

    val blogItemTitleWithSpacing: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockLinkSmallTitle(it).rules
        display = Display.inlineBlock
        marginTop = StyleValues.spacing.absolute7
    }

    val blogItemDescriptionWithSpacing: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockSmallerDescription(it).rules
        marginTop = StyleValues.spacing.absolute6
    }

    val viewAllButton: NamedRuleSet by css {
        marginTop = StyleValues.spacing.absolute27
    }

}



// Private - data.

private class ContentWhatsNewData(endpoints: WhatsNewMaterialEndpoints) {

    val blogItems: Array<BlogItem> = arrayOf(
        BlogItem(
            "md.designAndBuildForLargeScreens",
            "md.newGuidanceForAdaptingLayoutsAndComponents",
            Date(2021, 4,  14),
            endpoints.largeScreens
        ),
        BlogItem(
            "md.combinedComponentPages",
            "md.developerDocsAndDesignGuidelinesForComponents",
            Date(2020, 11, 18),
            endpoints.combinedComponentPages
        ),
        BlogItem(
            "md.materialDesignAwardWinners",
            "md.moooiEpsyAndKayak",
            Date(2020, 11, 14),
            endpoints.mdaWinners
        ),
    )

}
