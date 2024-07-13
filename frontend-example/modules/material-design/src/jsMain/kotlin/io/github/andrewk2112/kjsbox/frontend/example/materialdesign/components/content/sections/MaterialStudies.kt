@file:Suppress("FunctionName")

package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.sections

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.resources.endpoints.MainMaterialEndpoints
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.images.materialdesign.CraneImage
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.images.materialdesign.ReplyImage
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.images.materialdesign.ShrineImage
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.LocalizationKey
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useLocalizator
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context.ScreenSize.PHONE
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.useDesignTokensContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.buttons.RectButton
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.images.StrokedImage
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.useMaterialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.CRAFTING_COMMUNICATION_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.DESIGN_FOR_RETAIL_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.DISCOVER_THE_WAYS_MATERIAL_WAS_USED_TO_DESIGN_AND_BUILD_CRANE_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.GET_INSPIRED_BY_THE_WAYS_MATERIAL_ADAPTS_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.LEARN_HOW_MATERIAL_CAN_BE_USED_IN_RETAIL_PRODUCTS_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.MATERIAL_CAN_BE_USED_IN_COMMUNICATION_AND_PLANNING_PRODUCTS_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.MATERIAL_STUDIES_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.TRAVEL_TREATMENT_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.VIEW_ALL_KEY
import io.github.andrewk2112.kjsbox.frontend.image.resources.Image
import io.github.andrewk2112.utility.js.extensions.openBlankSafely
import io.github.andrewk2112.utility.react.components.FC
import io.github.andrewk2112.utility.react.hooks.useMemoWithReferenceCount
import kotlinx.browser.window
import kotlinx.css.*
import react.ChildrenBuilder
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.p
import react.useState



// Components.

val MaterialStudies by FC {

    val context              = useDesignTokensContext()
    val localizator          = useLocalizator()
    val component            = useMaterialDesignComponent()
    val materialDesignTokens = component.getMaterialDesignTokens()
    val styles               = useMemoWithReferenceCount(component) { MaterialStudiesStyles(materialDesignTokens) }
    val uiState             by useState { MaterialStudiesUiState() }

    Container(context, styles, materialDesignTokens) {
        Header(
            context,
            styles,
            localizator(MATERIAL_STUDIES_KEY),
            localizator(GET_INSPIRED_BY_THE_WAYS_MATERIAL_ADAPTS_KEY),
            localizator(VIEW_ALL_KEY),
            MainMaterialEndpoints.STUDIES
        )
        StudiesGrid(context, styles, materialDesignTokens) {
            for (study in uiState.studies) {
                it(
                    localizator(study.title),
                    localizator(study.description),
                    study.illustration,
                    localizator(study.illustrationAlternativeText)
                )
            }
        }
    }

}

private inline fun ChildrenBuilder.Container(
    context: Context,
    styles: MaterialStudiesStyles,
    materialDesignTokens: MaterialDesignTokens,
    crossinline children: ChildrenBuilder.() -> Unit
) =
    +div(clazz = styles.container(context).name) {
        +div(clazz = materialDesignTokens.component.layout.contentContainer.name, children)
    }

private fun ChildrenBuilder.Header(
    context: Context,
    styles: MaterialStudiesStyles,
    title: String,
    description: String,
    actionButtonLabel: String,
    actionButtonDestination: String,
) =
    +div(clazz = styles.header.name) {
        +h2(clazz = styles.title(context).name) { +title }
        +p(clazz = styles.description(context).name) { +description }
        +RectButton(clazz = styles.viewAllButton(context).name) {
            text   = actionButtonLabel
            action = { window.openBlankSafely(actionButtonDestination) }
            isDark = true
        }
    }

private inline fun ChildrenBuilder.StudiesGrid(
    context: Context,
    styles: MaterialStudiesStyles,
    materialDesignTokens: MaterialDesignTokens,
    crossinline studiesAdapter: ((title: String, description: String, Image, imageAltText: String) -> Unit) -> Unit
) =
    +div(clazz = styles.grid(context).name) {
        studiesAdapter { title, description, illustration, illustrationAlternativeText ->
            StudyItem(
                context, styles, materialDesignTokens,
                title, description, illustration, illustrationAlternativeText
            )
        }
    }

private fun ChildrenBuilder.StudyItem(
    context: Context,
    styles: MaterialStudiesStyles,
    materialDesignTokens: MaterialDesignTokens,
    title: String,
    description: String,
    illustration: Image,
    illustrationAlternativeText: String,
) =
    div { // ← to prevent the item from taking the entire height of the grid's row
        +div(clazz = materialDesignTokens.component.selection.hoverableWithIntensePaddedStroke(context).name) {
            +StrokedImage(clazz = materialDesignTokens.component.image.fitWidthKeepAspectImage.name) {
                image           = illustration
                alternativeText = illustrationAlternativeText
            }
            +p(clazz = styles.studyTitle(context).name) { +title }
            +p(clazz = styles.studyDescription(context).name) { +description }
        }
    }



// Styles.

private class MaterialStudiesStyles(
    private val materialDesignTokens: MaterialDesignTokens
) : DynamicStyleSheet(materialDesignTokens::class) {

    val container: DynamicCssProvider<Context> by dynamicCss {
        backgroundColor = materialDesignTokens.system.palette.surface1(it)
    }

    val header: NamedRuleSet by css {
        display  = Display.flex
        flexWrap = FlexWrap.wrap
        padding  = Padding(
            top   = materialDesignTokens.reference.spacing.absolute42,
            left  = materialDesignTokens.reference.spacing.absolute40,
            right = materialDesignTokens.reference.spacing.absolute40
        )
    }

    val title: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.label.contentBlockDarkTitle(it).rules
        flexBasis = FlexBasis.zero
        flexGrow  = 1
        if (!it.isCompactAppearance) {
            marginRight = materialDesignTokens.reference.spacing.absolute40
        }
    }

    val description: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.label.contentBlockDarkDescription(it).rules
        marginTop = materialDesignTokens.reference.spacing.absolute20
        if (!it.isCompactAppearance) {
            order = Order(2)
        }
    }

    val viewAllButton: DynamicCssProvider<Context> by dynamicCss {
        flexShrink = 0
        alignSelf  = Align.selfStart
        if (it.isCompactAppearance) {
            marginTop = materialDesignTokens.reference.spacing.absolute25
        } else {
            order = Order(1)
        }
    }

    val grid: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.layout.grid(it).rules
        padding = Padding(
            horizontal = materialDesignTokens.reference.spacing.absolute20,
            top        = if (it.isCompactAppearance) 0.px else materialDesignTokens.reference.spacing.absolute26,
            bottom     = materialDesignTokens.reference.spacing.absolute52
        )
    }

    val studyTitle: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.label.contentBlockDarkSubTitle(it).rules
        marginTop = materialDesignTokens.reference.spacing.absolute24
    }

    val studyDescription: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.label.contentBlockDarkSmallerDescription(it).rules
        marginTop = materialDesignTokens.reference.spacing.absolute10
    }

}

private inline val Context.isCompactAppearance: Boolean get() = screenSize <= PHONE



// UI state.

private class MaterialStudiesUiState private constructor(vararg val studies: MaterialStudyUiState) {

    constructor() : this(
        MaterialStudyUiState(
            DESIGN_FOR_RETAIL_KEY,
            LEARN_HOW_MATERIAL_CAN_BE_USED_IN_RETAIL_PRODUCTS_KEY,
            ShrineImage,
            DESIGN_FOR_RETAIL_KEY,
        ),
        MaterialStudyUiState(
            TRAVEL_TREATMENT_KEY,
            DISCOVER_THE_WAYS_MATERIAL_WAS_USED_TO_DESIGN_AND_BUILD_CRANE_KEY,
            CraneImage,
            TRAVEL_TREATMENT_KEY,
        ),
        MaterialStudyUiState(
            CRAFTING_COMMUNICATION_KEY,
            MATERIAL_CAN_BE_USED_IN_COMMUNICATION_AND_PLANNING_PRODUCTS_KEY,
            ReplyImage,
            CRAFTING_COMMUNICATION_KEY,
        ),
    )

}

private class MaterialStudyUiState(
    val title: LocalizationKey,
    val description: LocalizationKey,
    val illustration: Image,
    val illustrationAlternativeText: LocalizationKey,
)
