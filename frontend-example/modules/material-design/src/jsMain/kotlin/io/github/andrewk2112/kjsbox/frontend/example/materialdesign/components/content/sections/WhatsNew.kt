@file:Suppress("FunctionName")

package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.sections

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.resources.endpoints.WhatsNewMaterialEndpoints
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.LocalizationKey
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useCurrentLanguageAndLocalizator
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context.ScreenSize.SMALL_TABLET
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.useDesignTokensContext
import io.github.andrewk2112.kjsbox.frontend.example.localization.asIsoString
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.buttons.RectButton
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.useMaterialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.COMBINED_COMPONENT_PAGES_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.DESIGN_AND_BUILD_FOR_LARGE_SCREENS_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.DEVELOPER_DOCS_AND_DESIGN_GUIDELINES_FOR_COMPONENTS_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.MATERIAL_DESIGN_AWARD_WINNERS_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.MOOOI_EPSY_AND_KAYAK_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.NEW_GUIDANCE_FOR_ADAPTING_LAYOUTS_AND_COMPONENTS_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.THE_LATEST_MATERIAL_DESIGN_UPDATES_AND_GUIDANCE_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.VIEW_ALL_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.WHATS_NEW_KEY
import io.github.andrewk2112.kjsbox.frontend.example.sharedutility.DateTimeFormatOptions
import io.github.andrewk2112.utility.js.extensions.openBlankSafely
import io.github.andrewk2112.utility.react.dom.extensions.safeBlankHref
import io.github.andrewk2112.utility.react.hooks.useMemoWithReferenceCount
import js.intl.DateTimeFormat
import kotlinx.browser.window
import kotlinx.css.*
import react.ChildrenBuilder
import react.FC
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.useMemo
import react.useState
import kotlin.js.Date



// Components.

val WhatsNew = FC {

    val context                 = useDesignTokensContext()
    val (language, localizator) = useCurrentLanguageAndLocalizator()

    val component            = useMaterialDesignComponent()
    val materialDesignTokens = component.getMaterialDesignTokens()
    val styles               = useMemoWithReferenceCount(component) { WhatsNewStyles(materialDesignTokens) }

    val dateTimeFormat = useMemo(language) {
        DateTimeFormat(language.asIsoString(), DateTimeFormatOptions.LONG_DATE)
    }

    val endpoints by useState { WhatsNewMaterialEndpoints() }
    val uiState   by useState { WhatsNewUiState(endpoints) }

    Container(context, styles) {
        TitleLink(context, materialDesignTokens, localizator(WHATS_NEW_KEY), endpoints.whatsNew)
        Description(context, styles, localizator(THE_LATEST_MATERIAL_DESIGN_UPDATES_AND_GUIDANCE_KEY))
        BlogRecords(context, styles) {
            for (blogRecord in uiState.blogRecords) {
                it(
                    localizator(blogRecord.title),
                    localizator(blogRecord.description),
                    dateTimeFormat.format(blogRecord.date),
                    blogRecord.endpoint
                )
            }
        }
        ViewAllButton(styles, localizator(VIEW_ALL_KEY), endpoints.whatsNew)
    }

}

private inline fun ChildrenBuilder.Container(
    context: Context,
    styles: WhatsNewStyles,
    crossinline children: ChildrenBuilder.() -> Unit
) =
    +div(clazz = styles.container(context).name, children)

private fun ChildrenBuilder.TitleLink(
    context: Context,
    materialDesignTokens: MaterialDesignTokens,
    title: String,
    destinationEndpoint: String
) =
    +a(clazz = materialDesignTokens.component.label.contentBlockLinkTitle(context).name) {
        safeBlankHref = destinationEndpoint
        +title
    }

private fun ChildrenBuilder.Description(context: Context, styles: WhatsNewStyles, descriptionText: String) =
    +p(clazz = styles.description(context).name) { +descriptionText }

private inline fun ChildrenBuilder.BlogRecords(
    context: Context,
    styles: WhatsNewStyles,
    recordsAdapter: ((title: String, description: String, date: String, endpoint: String) -> Unit) -> Unit
) =
    recordsAdapter { title, description, formattedDate, destinationEndpoint ->
        BlogRecordItem(context, styles, title, description, formattedDate, destinationEndpoint)
    }

private fun ChildrenBuilder.BlogRecordItem(
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

private fun ChildrenBuilder.ViewAllButton(styles: WhatsNewStyles, label: String, destinationEndpoint: String) =
    +RectButton(clazz = styles.viewAllButton.name) {
        text   = label
        action = { window.openBlankSafely(destinationEndpoint) }
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
            DESIGN_AND_BUILD_FOR_LARGE_SCREENS_KEY,
            NEW_GUIDANCE_FOR_ADAPTING_LAYOUTS_AND_COMPONENTS_KEY,
            Date(2021, 4,  14),
            endpoints.largeScreens
        ),
        BlogRecordUiState(
            COMBINED_COMPONENT_PAGES_KEY,
            DEVELOPER_DOCS_AND_DESIGN_GUIDELINES_FOR_COMPONENTS_KEY,
            Date(2020, 11, 18),
            endpoints.combinedComponentPages
        ),
        BlogRecordUiState(
            MATERIAL_DESIGN_AWARD_WINNERS_KEY,
            MOOOI_EPSY_AND_KAYAK_KEY,
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
