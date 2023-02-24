package io.github.andrewk2112.md.components.content.sections

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.resourcewrappers.images.Image
import io.github.andrewk2112.resourcewrappers.images.materialdesign.MaterialDarkThemeImage
import io.github.andrewk2112.resourcewrappers.images.materialdesign.SoundGuidelinesImage
import io.github.andrewk2112.components.image
import io.github.andrewk2112.designtokens.Context.ScreenSize.PHONE
import io.github.andrewk2112.designtokens.Context.ScreenSize.SMALL_TABLET
import io.github.andrewk2112.localization.LocalizationKey
import io.github.andrewk2112.md.resources.endpoints.PopularMaterialEndpoints
import io.github.andrewk2112.md.styles.*
import io.github.andrewk2112.utility.safeBlankHref
import kotlinx.css.*
import kotlinx.css.properties.TextDecorationLine
import kotlinx.css.properties.textDecoration
import react.*
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.ul



// Components.

val designIntro = VFC {

    val context     = useAppContext()
    val localizator = useLocalizator()
    val uiState    by useState { DesignIntroUiState(PopularMaterialEndpoints()) }

    gridContainer(context) {
        titleAndCallToActionItem(
            context,
            localizator("md.design"),
            localizator("md.createIntuitiveAndBeautifulProductsWithMaterialDesign")
        )
        popularTopicsItem(context, localizator("md.popular")) {
            for (popularTopic in uiState.popularTopics) {
                it(localizator(popularTopic.title), popularTopic.destinationEndpoint)
            }
        }
        for ((index, topicPreview) in uiState.topicPreviews.withIndex()) {
            topicPreviewItem(
                context,
                hasDoubleWidth = index == 0,
                localizator(topicPreview.title),
                localizator(topicPreview.description),
                localizator(topicPreview.category),
                topicPreview.illustration,
                localizator(topicPreview.illustrationAlternativeText)
            )
        }
    }

}

/**
 * A root container with a background placing all its inner [children] as a grid.
 */
private inline fun ChildrenBuilder.gridContainer(context: Context, crossinline children: ChildrenBuilder.() -> Unit) =
    +div(DesignIntroStyles.container(context).name) {
        +div(DesignIntroStyles.grid(context).name, block = children)
    }

/**
 * The title and call to action block, takes the entire width on smaller screens.
 */
private fun ChildrenBuilder.titleAndCallToActionItem(context: Context, title: String, callToAction: String) =
    +div(LayoutStyles.gridDoubleOccupyingItem(context).name, DesignIntroStyles.horizontalSpacingGridItem.name) {
        +p(DesignIntroStyles.title(context).name) { +title }
        +p(DesignIntroStyles.callToAction(context).name) { +callToAction }
    }

/**
 * The list of popular topics with its subtitle.
 *
 * The [topicsAdapter] allows to retrieve all items to be rendered.
 */
private inline fun ChildrenBuilder.popularTopicsItem(
    context: Context,
    sectionSubtitle: String,
    crossinline topicsAdapter: ((title: String, destinationEndpoint: String) -> Unit) -> Unit,
) =
    +div(LayoutStyles.gridHidingItem(context).name, DesignIntroStyles.horizontalSpacingGridItem.name) {

        // Title.
        +p(DesignIntroStyles.category(context).name, DesignIntroStyles.popularTopicsCategory.name) {
            +sectionSubtitle.uppercase()
        }

        // Items.
        ul {
            topicsAdapter { title, destinationEndpoint ->
                +li(DesignIntroStyles.popularTopicItem(context).name) {
                    a {
                        safeBlankHref = destinationEndpoint
                        +title
                    }
                }
            }
        }

    }

/**
 * An item to render the preview for some topic.
 */
private fun ChildrenBuilder.topicPreviewItem(
    context: Context,
    hasDoubleWidth: Boolean,
    title: String,
    description: String,
    category: String,
    illustration: Image,
    illustrationAlternativeText: String,
) =
    +div(LayoutStyles.run { if (hasDoubleWidth) gridDoubleItem else gridItem }(context).name) {
        +div(SelectionStyles.hoverableWithIntensePaddedStroke(context).name) {
            image(illustration, illustrationAlternativeText, DesignIntroStyles.topicIllustration(context).name)
            +p(DesignIntroStyles.category(context).name, DesignIntroStyles.topicCategory.name) { +category.uppercase() }
            +p(DesignIntroStyles.topicTitle(context).name) { +title }
            +p(DesignIntroStyles.topicDescription(context).name) { +description }
        }
    }



// Styles.

private object DesignIntroStyles : DynamicStyleSheet() {

    val container: DynamicCssProvider<Context> by dynamicCss {
        backgroundColor = Theme.palette.surface1(it)
    }

    val grid: DynamicCssProvider<Context> by dynamicCss {
        +LayoutStyles.contentContainer.rules
        +LayoutStyles.grid(it).rules
        padding(
            top        = StyleValues.spacing.run { if (it.screenSize > SMALL_TABLET) absolute89 else absolute177 },
            horizontal = StyleValues.spacing.absolute20,
            bottom     = StyleValues.spacing.absolute52
        )
    }

    val horizontalSpacingGridItem: NamedRuleSet by css {
        padding(horizontal = StyleValues.spacing.absolute20)
    }

    val title: DynamicCssProvider<Context> by dynamicCss {
        fontSize = Theme.fontSizes.adaptive6(it)
        color = Theme.palette.onSurface1(it)
    }

    val callToAction: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockDarkDescription(it).rules
        marginTop = StyleValues.spacing.run { if (it.screenSize > PHONE) absolute20 else absolute16 }
        if (it.screenSize <= PHONE) {
            marginBottom = StyleValues.spacing.absolute16
        }
    }

    val category: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.mono.rules
        fontSize = StyleValues.fontSizes.relativep85
        color = Theme.palette.onSurfaceWeaker1(it)
    }

    val popularTopicsCategory: NamedRuleSet by css {
        marginTop = StyleValues.spacing.absolute1
    }

    val topicCategory: NamedRuleSet by css {
        marginTop = StyleValues.spacing.absolute24
    }

    val popularTopicItem: DynamicCssProvider<Context> by dynamicCss {
        display = Display.block
        val topSpacing = StyleValues.spacing.absolute6
        marginTop      = topSpacing
        marginBottom   = topSpacing + StyleValues.spacing.absolute5 // as the bottom margin interleaves with the next one
        children {
            +TransitionStyles.flashingTransition(::color).rules
            textDecoration(TextDecorationLine.underline)
            fontSize = StyleValues.fontSizes.relative1p2
            color = Theme.palette.onSurface1(it)
            hover {
                color = Theme.palette.onSurfaceFocused1(it)
            }
        }
    }

    val topicIllustration: DynamicCssProvider<Context> by dynamicCss {
        +StrokeStyles.outlineStroke(StrokeConfigs(it, StrokeColor.Intense)).rules
        width  = 100.pct
        height = LinearDimension.auto
    }

    val topicTitle: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockDarkSubTitle(it).rules
        marginTop = StyleValues.spacing.absolute6
    }

    val topicDescription: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockDarkSmallerDescription(it).rules
        marginTop = StyleValues.spacing.absolute11
    }

}



// UI state.

private class DesignIntroUiState private constructor(
    val popularTopics: Array<PopularTopicUiState>,
    val topicPreviews: Array<TopicPreviewUiState>,
) {

    constructor(endpoints: PopularMaterialEndpoints) : this(
        arrayOf(
            PopularTopicUiState("md.materialTheming", endpoints.materialTheming),
            PopularTopicUiState("md.iconography",     endpoints.iconography),
            PopularTopicUiState("md.textFields",      endpoints.textFields),
        ),
        arrayOf(
            TopicPreviewUiState(
                "md.materialDarkTheme",
                "md.learnHowToDesignADarkThemeVersionOfYourMaterialUI",
                "md.foundation",
                MaterialDarkThemeImage,
                "md.materialDarkTheme"
            ),
            TopicPreviewUiState(
                "md.materialGuidelines",
                "md.useSoundToCommunicateInformationInWaysThatAugment",
                "md.guidelines",
                SoundGuidelinesImage,
                "md.soundGuidelines"
            )
        )
    )

}

private class PopularTopicUiState(val title: LocalizationKey, val destinationEndpoint: String)

private class TopicPreviewUiState(
    val title: LocalizationKey,
    val description: LocalizationKey,
    val category: LocalizationKey,
    val illustration: Image,
    val illustrationAlternativeText: LocalizationKey,
)
