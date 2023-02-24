package io.github.andrewk2112.md.components.content.sections

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.Context.ScreenSize.PHONE
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.localization.LocalizationKey
import io.github.andrewk2112.md.components.common.buttons.rectButton
import io.github.andrewk2112.md.components.common.images.strokedImage
import io.github.andrewk2112.md.resources.endpoints.MainMaterialEndpoints
import io.github.andrewk2112.md.styles.LayoutStyles
import io.github.andrewk2112.md.styles.ImageStyles
import io.github.andrewk2112.md.styles.LabelStyles
import io.github.andrewk2112.md.styles.SelectionStyles
import io.github.andrewk2112.resourcewrappers.images.Image
import io.github.andrewk2112.resourcewrappers.images.materialdesign.CraneImage
import io.github.andrewk2112.resourcewrappers.images.materialdesign.ReplyImage
import io.github.andrewk2112.resourcewrappers.images.materialdesign.ShrineImage
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import io.github.andrewk2112.utility.openBlankWindowSafely
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
            localizator("md.materialStudies"),
            localizator("md.getInspiredByTheWaysMaterialAdapts"),
            localizator("md.viewAll"),
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
    +div(MaterialStudiesStyles.container(context).name) {
        +div(LayoutStyles.contentContainer.name, block = children)
    }

private fun ChildrenBuilder.header(
    context: Context,
    title: String,
    description: String,
    actionButtonLabel: String,
    actionButtonDestination: String,
) =
    +div(MaterialStudiesStyles.header.name) {
        +h2(MaterialStudiesStyles.title(context).name) { +title }
        +p(MaterialStudiesStyles.description(context).name) { +description }
        +rectButton(MaterialStudiesStyles.viewAllButton(context).name) {
            text   = actionButtonLabel
            action = { openBlankWindowSafely(actionButtonDestination) }
            isDark = true
        }
    }

private inline fun ChildrenBuilder.studiesGrid(
    context: Context,
    crossinline studiesAdapter: ((title: String, description: String, Image, imageAltText: String) -> Unit) -> Unit
) =
    +div(MaterialStudiesStyles.grid(context).name) {
        studiesAdapter { title, description, illustration, illustrationAlternativeText ->
            studyItem(context, title, description, illustration, illustrationAlternativeText)
        }
    }

private fun ChildrenBuilder.studyItem(
    context: Context,
    title: String,
    description: String,
    illustration: Image,
    illustrationAlternativeText: String,
) =
    div { // ← to prevent the item from taking the entire height of the grid's row
        +div(SelectionStyles.hoverableWithIntensePaddedStroke(context).name) {
            +strokedImage(ImageStyles.fitWidthKeepAspectImage.name) {
                image           = illustration
                alternativeText = illustrationAlternativeText
            }
            +p(MaterialStudiesStyles.studyTitle(context).name) { +title }
            +p(MaterialStudiesStyles.studyDescription(context).name) { +description }
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
            "md.designForRetail",
            "md.learnHowMaterialCanBeUsedInRetailProducts",
            ShrineImage,
            "md.designForRetail",
        ),
        MaterialStudyUiState(
            "md.travelTreatment",
            "md.discoverTheWaysMaterialWasUsedToDesignAndBuildCrane",
            CraneImage,
            "md.travelTreatment",
        ),
        MaterialStudyUiState(
            "md.craftingCommunication",
            "md.materialCanBeUsedInCommunicationAndPlanningProducts",
            ReplyImage,
            "md.craftingCommunication",
        ),
    )

}

private class MaterialStudyUiState(
    val title: LocalizationKey,
    val description: LocalizationKey,
    val illustration: Image,
    val illustrationAlternativeText: LocalizationKey,
)
