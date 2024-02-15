package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.sections

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.images.materialdesign.MaterialDarkThemeImage
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.images.materialdesign.SoundGuidelinesImage
import io.github.andrewk2112.kjsbox.frontend.core.localization.LocalizationKey
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.resources.endpoints.PopularMaterialEndpoints
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.*
import io.github.andrewk2112.kjsbox.frontend.core.utility.safeBlankHref
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useLocalizator
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.useRootComponent
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context.ScreenSize.PHONE
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context.ScreenSize.SMALL_TABLET
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.DesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.useDesignTokensContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.useMaterialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import io.github.andrewk2112.kjsbox.frontend.images.components.image
import io.github.andrewk2112.kjsbox.frontend.images.resources.Image
import io.github.andrewk2112.utility.react.hooks.useMemoWithReferenceCount
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.TextDecorationLine
import react.ChildrenBuilder
import react.FC
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.ul
import react.useState



// Components.

val designIntro = FC {

    val context                 = useDesignTokensContext()
    val localizator             = useLocalizator()
    val rootComponent           = useRootComponent()
    val materialDesignComponent = useMaterialDesignComponent()
    val materialDesignTokens    = materialDesignComponent.getMaterialDesignTokens()
    val styles                  = useMemoWithReferenceCount(rootComponent, materialDesignComponent) {
                                      DesignIntroStyles(rootComponent.getDesignTokens(), materialDesignTokens)
                                  }
    val uiState                by useState { DesignIntroUiState(PopularMaterialEndpoints()) }

    gridContainer(context, styles) {
        titleAndCallToActionItem(
            context,
            styles,
            materialDesignTokens,
            localizator(designKey),
            localizator(createIntuitiveAndBeautifulProductsWithMaterialDesignKey)
        )
        popularTopicsItem(context, styles, materialDesignTokens, localizator(popularKey)) {
            for (popularTopic in uiState.popularTopics) {
                it(localizator(popularTopic.title), popularTopic.destinationEndpoint)
            }
        }
        for ((index, topicPreview) in uiState.topicPreviews.withIndex()) {
            topicPreviewItem(
                context,
                styles,
                materialDesignTokens,
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
private inline fun ChildrenBuilder.gridContainer(
    context: Context,
    styles: DesignIntroStyles,
    crossinline children: ChildrenBuilder.() -> Unit
) =
    +div(clazz = styles.container(context).name) {
        +div(clazz = styles.grid(context).name, children)
    }

/**
 * The title and call to action block, takes the entire width on smaller screens.
 */
private fun ChildrenBuilder.titleAndCallToActionItem(
    context: Context,
    styles: DesignIntroStyles,
    materialDesignTokens: MaterialDesignTokens,
    title: String,
    callToAction: String
) =
    +div(
        materialDesignTokens.component.layout.gridDoubleOccupyingItem(context).name,
        styles.horizontalSpacingGridItem.name
    ) {
        +p(clazz = styles.title(context).name) { +title }
        +p(clazz = styles.callToAction(context).name) { +callToAction }
    }

/**
 * The list of popular topics with its subtitle.
 *
 * The [topicsAdapter] allows to retrieve all items to be rendered.
 */
private inline fun ChildrenBuilder.popularTopicsItem(
    context: Context,
    styles: DesignIntroStyles,
    materialDesignTokens: MaterialDesignTokens,
    sectionSubtitle: String,
    crossinline topicsAdapter: ((title: String, destinationEndpoint: String) -> Unit) -> Unit,
) =
    +div(
        materialDesignTokens.component.layout.gridHidingItem(context).name,
        styles.horizontalSpacingGridItem.name
    ) {

        // Title.
        +p(styles.category(context).name, styles.popularTopicsCategory.name) {
            +sectionSubtitle.uppercase()
        }

        // Items.
        ul {
            topicsAdapter { title, destinationEndpoint ->
                +li(clazz = styles.popularTopicItem(context).name) {
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
    styles: DesignIntroStyles,
    materialDesignTokens: MaterialDesignTokens,
    hasDoubleWidth: Boolean,
    title: String,
    description: String,
    category: String,
    illustration: Image,
    illustrationAlternativeText: String,
) =
    +div(
        clazz = materialDesignTokens.component.layout.run {
            if (hasDoubleWidth) gridDoubleItem else gridItem
        }(context).name
    ) {
        +div(clazz = materialDesignTokens.component.selection.hoverableWithIntensePaddedStroke(context).name) {
            image(illustration, illustrationAlternativeText, styles.topicIllustration(context).name)
            +p(styles.category(context).name, styles.topicCategory.name) { +category.uppercase() }
            +p(clazz = styles.topicTitle(context).name) { +title }
            +p(clazz = styles.topicDescription(context).name) { +description }
        }
    }



// Styles.

private class DesignIntroStyles(
    private val designTokens: DesignTokens,
    private val materialDesignTokens: MaterialDesignTokens
) : DynamicStyleSheet(designTokens::class, materialDesignTokens::class) {

    val container: DynamicCssProvider<Context> by dynamicCss {
        backgroundColor = materialDesignTokens.system.palette.surface1(it)
    }

    val grid: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.layout.contentContainer.rules
        +materialDesignTokens.component.layout.grid(it).rules
        padding = Padding(
            top        = materialDesignTokens.reference.spacing.run {
                             if (it.screenSize > SMALL_TABLET) absolute89 else absolute177
                         },
            horizontal = materialDesignTokens.reference.spacing.absolute20,
            bottom     = materialDesignTokens.reference.spacing.absolute52
        )
    }

    val horizontalSpacingGridItem: NamedRuleSet by css {
        padding = Padding(horizontal = materialDesignTokens.reference.spacing.absolute20)
    }

    val title: DynamicCssProvider<Context> by dynamicCss {
        fontSize = materialDesignTokens.system.fontSizes.adaptive5(it)
        color = materialDesignTokens.system.palette.onSurface1(it)
    }

    val callToAction: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.label.contentBlockDarkDescription(it).rules
        marginTop = materialDesignTokens.reference.spacing.run { if (it.screenSize > PHONE) absolute20 else absolute16 }
        if (it.screenSize <= PHONE) {
            marginBottom = materialDesignTokens.reference.spacing.absolute16
        }
    }

    val category: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.system.fontStyles.mono(it).rules
        fontSize = materialDesignTokens.reference.fontSizes.relative0p85
        color = materialDesignTokens.system.palette.onSurface1Weaker(it)
    }

    val popularTopicsCategory: NamedRuleSet by css {
        marginTop = materialDesignTokens.reference.spacing.absolute1
    }

    val topicCategory: NamedRuleSet by css {
        marginTop = materialDesignTokens.reference.spacing.absolute24
    }

    val popularTopicItem: DynamicCssProvider<Context> by dynamicCss {
        display = Display.block
        val topSpacing = materialDesignTokens.reference.spacing.absolute6
        marginTop      = topSpacing
        // As the bottom margin interleaves with the next one.
        marginBottom   = topSpacing + designTokens.reference.spacing.absolute5
        children {
            +materialDesignTokens.component.transition.flashing(::color).rules
            textDecoration = TextDecoration(setOf(TextDecorationLine.underline))
            fontSize       = materialDesignTokens.reference.fontSizes.relative1p2
            color = materialDesignTokens.system.palette.onSurface1(it)
            hover {
                color = materialDesignTokens.system.palette.onSurface1Focused(it)
            }
        }
    }

    val topicIllustration: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.stroke.lightOutlineStroke(it).rules
        width  = 100.pct
        height = LinearDimension.auto
    }

    val topicTitle: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.label.contentBlockDarkSubTitle(it).rules
        marginTop = materialDesignTokens.reference.spacing.absolute6
    }

    val topicDescription: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.label.contentBlockDarkSmallerDescription(it).rules
        marginTop = materialDesignTokens.reference.spacing.absolute11
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
    val illustration: Image,
    val illustrationAlternativeText: LocalizationKey,
)
