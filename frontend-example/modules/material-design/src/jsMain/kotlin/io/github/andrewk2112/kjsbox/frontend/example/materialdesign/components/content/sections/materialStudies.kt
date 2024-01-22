package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.sections

import io.github.andrewk2112.kjsbox.frontend.core.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.hooks.useMemoWithReferenceCount
import io.github.andrewk2112.kjsbox.frontend.core.localization.LocalizationKey
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.buttons.rectButton
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.images.strokedImage
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.resources.endpoints.MainMaterialEndpoints
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.images.materialdesign.CraneImage
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.images.materialdesign.ReplyImage
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.images.materialdesign.ShrineImage
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.*
import io.github.andrewk2112.kjsbox.frontend.core.resources.Image as ResourceImage
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.core.utility.openBlankWindowSafely
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useLocalizator
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context.ScreenSize.PHONE
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

val materialStudies = FC {

    val context              = useDesignTokensContext()
    val localizator          = useLocalizator()
    val component            = useMaterialDesignComponent()
    val materialDesignTokens = component.getMaterialDesignTokens()
    val styles               = useMemoWithReferenceCount(component) { MaterialStudiesStyles(materialDesignTokens) }
    val uiState             by useState { MaterialStudiesUiState() }

    container(context, styles, materialDesignTokens) {
        header(
            context,
            styles,
            localizator(materialStudiesKey),
            localizator(getInspiredByTheWaysMaterialAdaptsKey),
            localizator(viewAllKey),
            MainMaterialEndpoints.studies
        )
        studiesGrid(context, styles, materialDesignTokens) {
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

private inline fun ChildrenBuilder.container(
    context: Context,
    styles: MaterialStudiesStyles,
    materialDesignTokens: MaterialDesignTokens,
    crossinline children: ChildrenBuilder.() -> Unit
) =
    +div(clazz = styles.container(context).name) {
        +div(clazz = materialDesignTokens.component.layout.contentContainer.name, children)
    }

private fun ChildrenBuilder.header(
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
        +rectButton(clazz = styles.viewAllButton(context).name) {
            text   = actionButtonLabel
            action = { openBlankWindowSafely(actionButtonDestination) }
            isDark = true
        }
    }

private inline fun ChildrenBuilder.studiesGrid(
    context: Context,
    styles: MaterialStudiesStyles,
    materialDesignTokens: MaterialDesignTokens,
    crossinline studiesAdapter: ((title: String, description: String, ResourceImage, imageAltText: String) -> Unit) -> Unit
) =
    +div(clazz = styles.grid(context).name) {
        studiesAdapter { title, description, illustration, illustrationAlternativeText ->
            studyItem(
                context, styles, materialDesignTokens,
                title, description, illustration, illustrationAlternativeText
            )
        }
    }

private fun ChildrenBuilder.studyItem(
    context: Context,
    styles: MaterialStudiesStyles,
    materialDesignTokens: MaterialDesignTokens,
    title: String,
    description: String,
    illustration: ResourceImage,
    illustrationAlternativeText: String,
) =
    div { // ← to prevent the item from taking the entire height of the grid's row
        +div(clazz = materialDesignTokens.component.selection.hoverableWithIntensePaddedStroke(context).name) {
            +strokedImage(clazz = materialDesignTokens.component.image.fitWidthKeepAspectImage.name) {
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
            designForRetailKey,
            learnHowMaterialCanBeUsedInRetailProductsKey,
            ShrineImage,
            designForRetailKey,
        ),
        MaterialStudyUiState(
            travelTreatmentKey,
            discoverTheWaysMaterialWasUsedToDesignAndBuildCraneKey,
            CraneImage,
            travelTreatmentKey,
        ),
        MaterialStudyUiState(
            craftingCommunicationKey,
            materialCanBeUsedInCommunicationAndPlanningProductsKey,
            ReplyImage,
            craftingCommunicationKey,
        ),
    )

}

private class MaterialStudyUiState(
    val title: LocalizationKey,
    val description: LocalizationKey,
    val illustration: ResourceImage,
    val illustrationAlternativeText: LocalizationKey,
)
