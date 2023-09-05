package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.sections

import io.github.andrewk2112.kjsbox.frontend.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.designtokens.Context.ScreenSize.PHONE
import io.github.andrewk2112.kjsbox.frontend.designtokens.StyleValues
import io.github.andrewk2112.kjsbox.frontend.designtokens.Theme
import io.github.andrewk2112.kjsbox.frontend.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.hooks.useAppContext
import io.github.andrewk2112.kjsbox.frontend.hooks.useLocalizator
import io.github.andrewk2112.kjsbox.frontend.localization.LocalizationKey
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.buttons.rectButton
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.images.strokedImage
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.resources.endpoints.MainMaterialEndpoints
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles.LayoutStyles
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles.ImageStyles
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles.LabelStyles
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles.SelectionStyles
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.images.materialdesign.CraneImage
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.images.materialdesign.ReplyImage
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.images.materialdesign.ShrineImage
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.*
import io.github.andrewk2112.kjsbox.frontend.resources.Image as ResourceImage
import io.github.andrewk2112.kjsbox.frontend.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.utility.openBlankWindowSafely
import kotlinx.css.*
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.p



// Components.

val materialStudies = VFC {

    val context     = useAppContext()
    val localizator = useLocalizator()
    val uiState    by useState { MaterialStudiesUiState() }

    container(context) {
        header(
            context,
            localizator(materialStudiesKey),
            localizator(getInspiredByTheWaysMaterialAdaptsKey),
            localizator(viewAllKey),
            MainMaterialEndpoints.studies
        )
        studiesGrid(context) {
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

private inline fun ChildrenBuilder.container(context: Context, crossinline children: ChildrenBuilder.() -> Unit) =
    +div(clazz = MaterialStudiesStyles.container(context).name) {
        +div(clazz = LayoutStyles.contentContainer.name, children)
    }

private fun ChildrenBuilder.header(
    context: Context,
    title: String,
    description: String,
    actionButtonLabel: String,
    actionButtonDestination: String,
) =
    +div(clazz = MaterialStudiesStyles.header.name) {
        +h2(clazz = MaterialStudiesStyles.title(context).name) { +title }
        +p(clazz = MaterialStudiesStyles.description(context).name) { +description }
        +rectButton(clazz = MaterialStudiesStyles.viewAllButton(context).name) {
            text   = actionButtonLabel
            action = { openBlankWindowSafely(actionButtonDestination) }
            isDark = true
        }
    }

private inline fun ChildrenBuilder.studiesGrid(
    context: Context,
    crossinline studiesAdapter: ((title: String, description: String, ResourceImage, imageAltText: String) -> Unit) -> Unit
) =
    +div(clazz = MaterialStudiesStyles.grid(context).name) {
        studiesAdapter { title, description, illustration, illustrationAlternativeText ->
            studyItem(context, title, description, illustration, illustrationAlternativeText)
        }
    }

private fun ChildrenBuilder.studyItem(
    context: Context,
    title: String,
    description: String,
    illustration: ResourceImage,
    illustrationAlternativeText: String,
) =
    div { // ← to prevent the item from taking the entire height of the grid's row
        +div(clazz = SelectionStyles.hoverableWithIntensePaddedStroke(context).name) {
            +strokedImage(clazz = ImageStyles.fitWidthKeepAspectImage.name) {
                image           = illustration
                alternativeText = illustrationAlternativeText
            }
            +p(clazz = MaterialStudiesStyles.studyTitle(context).name) { +title }
            +p(clazz = MaterialStudiesStyles.studyDescription(context).name) { +description }
        }
    }



// Styles.

private object MaterialStudiesStyles : DynamicStyleSheet() {

    val container: DynamicCssProvider<Context> by dynamicCss {
        backgroundColor = Theme.palette.surface1(it)
    }

    val header: NamedRuleSet by css {
        display  = Display.flex
        flexWrap = FlexWrap.wrap
        padding(
            top   = StyleValues.spacing.absolute42,
            left  = StyleValues.spacing.absolute40,
            right = StyleValues.spacing.absolute40
        )
    }

    val title: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockDarkTitle(it).rules
        flexBasis = FlexBasis.zero
        flexGrow  = 1
        if (!it.isCompactAppearance) {
            marginRight = StyleValues.spacing.absolute40
        }
    }

    val description: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockDarkDescription(it).rules
        marginTop = StyleValues.spacing.absolute20
        if (!it.isCompactAppearance) {
            order = Order(2)
        }
    }

    val viewAllButton: DynamicCssProvider<Context> by dynamicCss {
        flexShrink = 0
        alignSelf  = Align.selfStart
        if (it.isCompactAppearance) {
            marginTop = StyleValues.spacing.absolute25
        } else {
            order = Order(1)
        }
    }

    val grid: DynamicCssProvider<Context> by dynamicCss {
        +LayoutStyles.grid(it).rules
        padding(
            horizontal = StyleValues.spacing.absolute20,
            top        = if (it.isCompactAppearance) 0.px else StyleValues.spacing.absolute26,
            bottom     = StyleValues.spacing.absolute52
        )
    }

    val studyTitle: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockDarkSubTitle(it).rules
        marginTop = StyleValues.spacing.absolute24
    }

    val studyDescription: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockDarkSmallerDescription(it).rules
        marginTop = StyleValues.spacing.absolute10
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
