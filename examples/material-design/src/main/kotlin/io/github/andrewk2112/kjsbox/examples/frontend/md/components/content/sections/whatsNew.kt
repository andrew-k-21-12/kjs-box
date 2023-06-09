package io.github.andrewk2112.kjsbox.examples.frontend.md.components.content.sections

import io.github.andrewk2112.kjsbox.frontend.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.designtokens.Context.ScreenSize.SMALL_TABLET
import io.github.andrewk2112.kjsbox.frontend.designtokens.StyleValues
import io.github.andrewk2112.kjsbox.frontend.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.hooks.useAppContext
import io.github.andrewk2112.kjsbox.frontend.hooks.useCurrentLanguageAndLocalizator
import io.github.andrewk2112.kjsbox.frontend.localization.LocalizationKey
import io.github.andrewk2112.kjsbox.examples.frontend.md.components.common.buttons.rectButton
import io.github.andrewk2112.kjsbox.examples.frontend.md.resources.endpoints.WhatsNewMaterialEndpoints
import io.github.andrewk2112.kjsbox.examples.frontend.md.styles.LayoutStyles
import io.github.andrewk2112.kjsbox.examples.frontend.md.styles.LabelStyles
import io.github.andrewk2112.kjsbox.examples.frontend.resourcewrappers.locales.materialdesign.*
import io.github.andrewk2112.kjsbox.frontend.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.utility.date.LongDateOnlyFormat
import io.github.andrewk2112.kjsbox.frontend.utility.openBlankWindowSafely
import io.github.andrewk2112.kjsbox.frontend.utility.safeBlankHref
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
        titleLink(context, localizator(whatsNewKey), endpoints.whatsNew)
        description(context, localizator(theLatestMaterialDesignUpdatesAndGuidanceKey))
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
        viewAllButton(localizator(viewAllKey), endpoints.whatsNew)
    }

}

private inline fun ChildrenBuilder.container(context: Context, crossinline children: ChildrenBuilder.() -> Unit) =
    +div(clazz = WhatsNewStyles.container(context).name, children)

private fun ChildrenBuilder.titleLink(context: Context, title: String, destinationEndpoint: String) =
    +a(clazz = LabelStyles.contentBlockLinkTitle(context).name) {
        safeBlankHref = destinationEndpoint
        +title
    }

private fun ChildrenBuilder.description(context: Context, descriptionText: String) =
    +p(clazz = WhatsNewStyles.description(context).name) { +descriptionText }

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
    +p(clazz = WhatsNewStyles.blogRecordDate(context).name) { +formattedDate.uppercase() }
    +a(clazz = WhatsNewStyles.blogRecordTitle(context).name) {
        safeBlankHref = destinationEndpoint
        +title
    }
    +p(clazz = WhatsNewStyles.blogRecordDescription(context).name) { +description }
}

private fun ChildrenBuilder.viewAllButton(label: String, destinationEndpoint: String) =
    +rectButton(clazz = WhatsNewStyles.viewAllButton.name) {
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
            designAndBuildForLargeScreensKey,
            newGuidanceForAdaptingLayoutsAndComponentsKey,
            Date(2021, 4,  14),
            endpoints.largeScreens
        ),
        BlogRecordUiState(
            combinedComponentPagesKey,
            developerDocsAndDesignGuidelinesForComponentsKey,
            Date(2020, 11, 18),
            endpoints.combinedComponentPages
        ),
        BlogRecordUiState(
            materialDesignAwardWinnersKey,
            moooiEpsyAndKayakKey,
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
