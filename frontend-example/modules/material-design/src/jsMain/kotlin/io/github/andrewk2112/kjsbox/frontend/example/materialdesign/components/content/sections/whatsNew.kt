package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.sections

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.hooks.useMemoWithReferenceCount
import io.github.andrewk2112.kjsbox.frontend.core.localization.LocalizationKey
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.buttons.rectButton
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.resources.endpoints.WhatsNewMaterialEndpoints
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.*
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.core.utility.date.LongDateOnlyFormat
import io.github.andrewk2112.kjsbox.frontend.core.utility.openBlankWindowSafely
import io.github.andrewk2112.kjsbox.frontend.core.utility.safeBlankHref
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useCurrentLanguageAndLocalizator
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context.ScreenSize.SMALL_TABLET
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.useDesignTokensContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.useMaterialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import kotlinx.css.*
import react.ChildrenBuilder
import react.FC
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.useState
import kotlin.js.Date



// Components.

val whatsNew = FC {

    val context                 = useDesignTokensContext()
    val (language, localizator) = useCurrentLanguageAndLocalizator()

    val component            = useMaterialDesignComponent()
    val materialDesignTokens = component.getMaterialDesignTokens()
    val styles               = useMemoWithReferenceCount(component) { WhatsNewStyles(materialDesignTokens) }

    val dateFormat by useState { LongDateOnlyFormat() }

    val endpoints by useState { WhatsNewMaterialEndpoints() }
    val uiState   by useState { WhatsNewUiState(endpoints) }

    container(context, styles) {
        titleLink(context, materialDesignTokens, localizator(whatsNewKey), endpoints.whatsNew)
        description(context, styles, localizator(theLatestMaterialDesignUpdatesAndGuidanceKey))
        blogRecords(context, styles) {
            for (blogRecord in uiState.blogRecords) {
                it(
                    localizator(blogRecord.title),
                    localizator(blogRecord.description),
                    dateFormat.format(blogRecord.date, language),
                    blogRecord.endpoint
                )
            }
        }
        viewAllButton(styles, localizator(viewAllKey), endpoints.whatsNew)
    }

}

private inline fun ChildrenBuilder.container(
    context: Context,
    styles: WhatsNewStyles,
    crossinline children: ChildrenBuilder.() -> Unit
) =
    +div(clazz = styles.container(context).name, children)

private fun ChildrenBuilder.titleLink(
    context: Context,
    materialDesignTokens: MaterialDesignTokens,
    title: String,
    destinationEndpoint: String
) =
    +a(clazz = materialDesignTokens.component.label.contentBlockLinkTitle(context).name) {
        safeBlankHref = destinationEndpoint
        +title
    }

private fun ChildrenBuilder.description(context: Context, styles: WhatsNewStyles, descriptionText: String) =
    +p(clazz = styles.description(context).name) { +descriptionText }

private inline fun ChildrenBuilder.blogRecords(
    context: Context,
    styles: WhatsNewStyles,
    recordsAdapter: ((title: String, description: String, date: String, endpoint: String) -> Unit) -> Unit
) =
    recordsAdapter { title, description, formattedDate, destinationEndpoint ->
        blogRecordItem(context, styles, title, description, formattedDate, destinationEndpoint)
    }

private fun ChildrenBuilder.blogRecordItem(
    context: Context,
    styles: WhatsNewStyles,
    title: String,
    description: String,
    formattedDate: String,
    destinationEndpoint: String
) {
    +p(clazz = styles.blogRecordDate(context).name) { +formattedDate.uppercase() }
    +a(clazz = styles.blogRecordTitle(context).name) {
        safeBlankHref = destinationEndpoint
        +title
    }
    +p(clazz = styles.blogRecordDescription(context).name) { +description }
}

private fun ChildrenBuilder.viewAllButton(styles: WhatsNewStyles, label: String, destinationEndpoint: String) =
    +rectButton(clazz = styles.viewAllButton.name) {
        text   = label
        action = { openBlankWindowSafely(destinationEndpoint) }
    }



// Styles.

private class WhatsNewStyles(
    private val materialDesignTokens: MaterialDesignTokens
) : DynamicStyleSheet(materialDesignTokens::class) {

    val container: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.layout.contentContainer.rules
        padding = Padding(
            top        = materialDesignTokens.reference.spacing.run {
                             if (it.screenSize >= SMALL_TABLET) absolute89 else absolute64
                         },
            bottom     = materialDesignTokens.reference.spacing.absolute47,
            horizontal = materialDesignTokens.reference.spacing.absolute40
        )
    }

    val description: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.label.contentBlockDescription(it).rules
        marginTop    = materialDesignTokens.reference.spacing.absolute12
        marginBottom = materialDesignTokens.reference.spacing.absolute38
    }

    val blogRecordDate: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.label.contentBlockDate(it).rules
        marginTop = materialDesignTokens.reference.spacing.absolute27
    }

    val blogRecordTitle: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.label.contentBlockLinkSmallTitle(it).rules
        display = Display.inlineBlock
        marginTop = materialDesignTokens.reference.spacing.absolute7
    }

    val blogRecordDescription: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.label.contentBlockDescription(it).rules
        marginTop = materialDesignTokens.reference.spacing.absolute6
    }

    val viewAllButton: NamedRuleSet by css {
        marginTop = materialDesignTokens.reference.spacing.absolute27
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
