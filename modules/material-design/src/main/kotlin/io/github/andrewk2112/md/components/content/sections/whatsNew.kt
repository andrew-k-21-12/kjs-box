package io.github.andrewk2112.md.components.content.sections

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.Context.ScreenSize.SMALL_TABLET
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useCurrentLanguageAndLocalizator
import io.github.andrewk2112.localization.LocalizationKey
import io.github.andrewk2112.md.components.common.buttons.rectButton
import io.github.andrewk2112.md.resources.endpoints.WhatsNewMaterialEndpoints
import io.github.andrewk2112.md.styles.LayoutStyles
import io.github.andrewk2112.md.styles.LabelStyles
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import io.github.andrewk2112.utility.date.LongDateOnlyFormat
import io.github.andrewk2112.utility.openBlankWindowSafely
import io.github.andrewk2112.utility.safeBlankHref
import kotlinx.css.*
import react.*
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import kotlin.js.Date



// Components.

val whatsNew = VFC {

    val context                 = useAppContext()
    val (language, localizator) = useCurrentLanguageAndLocalizator()

    val dateFormat by useState { LongDateOnlyFormat() }

    val endpoints by useState { WhatsNewMaterialEndpoints() }
    val uiState   by useState { WhatsNewUiState(endpoints) }

    container(context) {
        titleLink(context, localizator("md.whatsNew"), endpoints.whatsNew)
        description(context, localizator("md.theLatestMaterialDesignUpdatesAndGuidance"))
        blogRecords(context) {
            for (blogRecord in uiState.blogRecords) {
                it(
                    localizator(blogRecord.title),
                    localizator(blogRecord.description),
                    dateFormat.format(blogRecord.date, language),
                    blogRecord.endpoint
                )
            }
        }
        viewAllButton(localizator("md.viewAll"), endpoints.whatsNew)
    }

}

private inline fun ChildrenBuilder.container(context: Context, crossinline children: ChildrenBuilder.() -> Unit) =
    +div(WhatsNewStyles.container(context).name, block = children)

private fun ChildrenBuilder.titleLink(context: Context, title: String, destinationEndpoint: String) =
    +a(LabelStyles.contentBlockLinkTitle(context).name) {
        safeBlankHref = destinationEndpoint
        +title
    }

private fun ChildrenBuilder.description(context: Context, descriptionText: String) =
    +p(WhatsNewStyles.description(context).name) { +descriptionText }

private inline fun ChildrenBuilder.blogRecords(
    context: Context,
    recordsAdapter: ((title: String, description: String, date: String, endpoint: String) -> Unit) -> Unit
) =
    recordsAdapter { title, description, formattedDate, destinationEndpoint ->
        blogRecordItem(context, title, description, formattedDate, destinationEndpoint)
    }

private fun ChildrenBuilder.blogRecordItem(
    context: Context,
    title: String,
    description: String,
    formattedDate: String,
    destinationEndpoint: String
) {
    +p(WhatsNewStyles.blogRecordDate(context).name) { +formattedDate.uppercase() }
    +a(WhatsNewStyles.blogRecordTitle(context).name) {
        safeBlankHref = destinationEndpoint
        +title
    }
    +p(WhatsNewStyles.blogRecordDescription(context).name) { +description }
}

private fun ChildrenBuilder.viewAllButton(label: String, destinationEndpoint: String) =
    +rectButton(WhatsNewStyles.viewAllButton.name) {
        text   = label
        action = { openBlankWindowSafely(destinationEndpoint) }
    }



// Styles.

private object WhatsNewStyles : DynamicStyleSheet() {

    val container: DynamicCssProvider<Context> by dynamicCss {
        +LayoutStyles.contentContainer.rules
        padding(
            top        = StyleValues.spacing.run { if (it.screenSize >= SMALL_TABLET) absolute89 else absolute64 },
            bottom     = StyleValues.spacing.absolute47,
            horizontal = StyleValues.spacing.absolute40
        )
    }

    val description: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockDescription(it).rules
        marginTop    = StyleValues.spacing.absolute12
        marginBottom = StyleValues.spacing.absolute38
    }

    val blogRecordDate: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockDate(it).rules
        marginTop = StyleValues.spacing.absolute27
    }

    val blogRecordTitle: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockLinkSmallTitle(it).rules
        display = Display.inlineBlock
        marginTop = StyleValues.spacing.absolute7
    }

    val blogRecordDescription: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockDescription(it).rules
        marginTop = StyleValues.spacing.absolute6
    }

    val viewAllButton: NamedRuleSet by css {
        marginTop = StyleValues.spacing.absolute27
    }

}



// UI state.

private class WhatsNewUiState private constructor(vararg val blogRecords: BlogRecordUiState) {

    constructor(endpoints: WhatsNewMaterialEndpoints) : this(
        BlogRecordUiState(
            "md.designAndBuildForLargeScreens",
            "md.newGuidanceForAdaptingLayoutsAndComponents",
            Date(2021, 4,  14),
            endpoints.largeScreens
        ),
        BlogRecordUiState(
            "md.combinedComponentPages",
            "md.developerDocsAndDesignGuidelinesForComponents",
            Date(2020, 11, 18),
            endpoints.combinedComponentPages
        ),
        BlogRecordUiState(
            "md.materialDesignAwardWinners",
            "md.moooiEpsyAndKayak",
            Date(2020, 11, 14),
            endpoints.mdaWinners
        ),
    )

}

private class BlogRecordUiState(
    val title: LocalizationKey,
    val description: LocalizationKey,
    val date: Date,
    val endpoint: String,
)
