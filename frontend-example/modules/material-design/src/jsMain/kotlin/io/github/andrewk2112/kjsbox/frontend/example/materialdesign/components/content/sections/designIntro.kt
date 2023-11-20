package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.sections

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.core.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.images.materialdesign.MaterialDarkThemeImage
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.images.materialdesign.SoundGuidelinesImage
import io.github.andrewk2112.kjsbox.frontend.core.components.image
import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context.ScreenSize.PHONE
import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context.ScreenSize.SMALL_TABLET
import io.github.andrewk2112.kjsbox.frontend.core.localization.LocalizationKey
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.resources.endpoints.PopularMaterialEndpoints
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles.*
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.*
import io.github.andrewk2112.kjsbox.frontend.core.resources.Image as ResourceImage
import io.github.andrewk2112.kjsbox.frontend.core.utility.safeBlankHref
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.accessors.DesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.hooks.useAppContext
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.hooks.useLocalizator
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.accessors.MaterialDesignTokens
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.TextDecorationLine
import react.*
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.ul



// Components.

val designIntro = FC {

    val context     = useAppContext()
    val localizator = useLocalizator()
    val uiState    by useState { DesignIntroUiState(PopularMaterialEndpoints()) }

    gridContainer(context) {
        titleAndCallToActionItem(
            context,
            localizator(designKey),
            localizator(createIntuitiveAndBeautifulProductsWithMaterialDesignKey)
        )
        popularTopicsItem(context, localizator(popularKey)) {
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
    +div(clazz = DesignIntroStyles.container(context).name) {
        +div(clazz = DesignIntroStyles.grid(context).name, children)
    }

/**
 * The title and call to action block, takes the entire width on smaller screens.
 */
private fun ChildrenBuilder.titleAndCallToActionItem(context: Context, title: String, callToAction: String) =
    +div(LayoutStyles.gridDoubleOccupyingItem(context).name, DesignIntroStyles.horizontalSpacingGridItem.name) {
        +p(clazz = DesignIntroStyles.title(context).name) { +title }
        +p(clazz = DesignIntroStyles.callToAction(context).name) { +callToAction }
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
                +li(clazz = DesignIntroStyles.popularTopicItem(context).name) {
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
    illustration: ResourceImage,
    illustrationAlternativeText: String,
) =
    +div(clazz = LayoutStyles.run { if (hasDoubleWidth) gridDoubleItem else gridItem }(context).name) {
        +div(clazz = SelectionStyles.hoverableWithIntensePaddedStroke(context).name) {
            image(illustration, illustrationAlternativeText, DesignIntroStyles.topicIllustration(context).name)
            +p(DesignIntroStyles.category(context).name, DesignIntroStyles.topicCategory.name) { +category.uppercase() }
            +p(clazz = DesignIntroStyles.topicTitle(context).name) { +title }
            +p(clazz = DesignIntroStyles.topicDescription(context).name) { +description }
        }
    }



// Styles.

private object DesignIntroStyles : DynamicStyleSheet() {

    val container: DynamicCssProvider<Context> by dynamicCss {
        backgroundColor = MaterialDesignTokens.system.palette.surface1(it)
    }

    val grid: DynamicCssProvider<Context> by dynamicCss {
        +LayoutStyles.contentContainer.rules
        +LayoutStyles.grid(it).rules
        padding = Padding(
            top        = MaterialDesignTokens.reference.spacing.run {
                             if (it.screenSize > SMALL_TABLET) absolute89 else absolute177
                         },
            horizontal = MaterialDesignTokens.reference.spacing.absolute20,
            bottom     = MaterialDesignTokens.reference.spacing.absolute52
        )
    }

    val horizontalSpacingGridItem: NamedRuleSet by css {
        padding = Padding(horizontal = MaterialDesignTokens.reference.spacing.absolute20)
    }

    val title: DynamicCssProvider<Context> by dynamicCss {
        fontSize = MaterialDesignTokens.system.fontSizes.adaptive5(it)
        color = MaterialDesignTokens.system.palette.onSurface1(it)
    }

    val callToAction: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockDarkDescription(it).rules
        marginTop = MaterialDesignTokens.reference.spacing.run { if (it.screenSize > PHONE) absolute20 else absolute16 }
        if (it.screenSize <= PHONE) {
            marginBottom = MaterialDesignTokens.reference.spacing.absolute16
        }
    }

    val category: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.mono.rules
        fontSize = MaterialDesignTokens.reference.fontSizes.relative0p85
        color = MaterialDesignTokens.system.palette.onSurface1Weaker(it)
    }

    val popularTopicsCategory: NamedRuleSet by css {
        marginTop = MaterialDesignTokens.reference.spacing.absolute1
    }

    val topicCategory: NamedRuleSet by css {
        marginTop = MaterialDesignTokens.reference.spacing.absolute24
    }

    val popularTopicItem: DynamicCssProvider<Context> by dynamicCss {
        display = Display.block
        val topSpacing = MaterialDesignTokens.reference.spacing.absolute6
        marginTop      = topSpacing
        // As the bottom margin interleaves with the next one.
        marginBottom   = topSpacing + DesignTokens.reference.spacing.absolute5
        children {
            +TransitionStyles.flashingTransition(::color).rules
            textDecoration = TextDecoration(setOf(TextDecorationLine.underline))
            fontSize       = MaterialDesignTokens.reference.fontSizes.relative1p2
            color = MaterialDesignTokens.system.palette.onSurface1(it)
            hover {
                color = MaterialDesignTokens.system.palette.onSurface1Focused(it)
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
        marginTop = MaterialDesignTokens.reference.spacing.absolute6
    }

    val topicDescription: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockDarkSmallerDescription(it).rules
        marginTop = MaterialDesignTokens.reference.spacing.absolute11
    }

}



// UI state.

private class DesignIntroUiState private constructor(
    val popularTopics: Array<PopularTopicUiState>,
    val topicPreviews: Array<TopicPreviewUiState>,
) {

    constructor(endpoints: PopularMaterialEndpoints) : this(
        arrayOf(
            PopularTopicUiState(materialThemingKey, endpoints.materialTheming),
            PopularTopicUiState(iconographyKey,     endpoints.iconography),
            PopularTopicUiState(textFieldsKey,      endpoints.textFields),
        ),
        arrayOf(
            TopicPreviewUiState(
                materialDarkThemeKey,
                learnHowToDesignADarkThemeVersionOfYourMaterialUIKey,
                foundationKey,
                MaterialDarkThemeImage,
                materialDarkThemeKey
            ),
            TopicPreviewUiState(
                materialGuidelinesKey,
                useSoundToCommunicateInformationInWaysThatAugmentKey,
                guidelinesKey,
                SoundGuidelinesImage,
                soundGuidelinesKey
            )
        )
    )

}

private class PopularTopicUiState(val title: LocalizationKey, val destinationEndpoint: String)

private class TopicPreviewUiState(
    val title: LocalizationKey,
    val description: LocalizationKey,
    val category: LocalizationKey,
    val illustration: ResourceImage,
    val illustrationAlternativeText: LocalizationKey,
)
