package io.github.andrewk2112.md.components.content

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.resources.images.Image
import io.github.andrewk2112.resources.images.MdMaterialDarkThemeImage
import io.github.andrewk2112.resources.images.MdSoundGuidelinesImage
import io.github.andrewk2112.components.image
import io.github.andrewk2112.md.resources.endpoints.PopularMaterialEndpoints
import io.github.andrewk2112.md.styles.*
import io.github.andrewk2112.utility.safeBlankHref
import kotlinx.css.*
import kotlinx.css.properties.TextDecorationLine
import kotlinx.css.properties.textDecoration
import react.ChildrenBuilder
import react.FC
import react.Props
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.ul
import react.useState

// Public.

val contentDesign = FC<Props> {

    // State.

    val context     = useAppContext()
    val localizator = useLocalizator()
    val endpoints  by useState { PopularMaterialEndpoints() }



    // Rendering.

    // A grid with contents.
    +div(ContentDesignStyles.gridContainer(context).name) {

        // Block with a title and caption.
        +div(ContentDesignStyles.horizontalSpacingGridBlock(context).name) {

            // Title.
            +p(ContentDesignStyles.bigLabel(context).name) { +localizator("md.design") }

            // Caption.
            +p(ContentDesignStyles.captionLabel(context).name) {
                +localizator("md.createIntuitiveAndBeautifulProductsWithMaterialDesign")
            }

        }

        // Block with a popular topics list.
        +div(ContentDesignStyles.horizontalSpacingGridBlock(context).name) {

            // Topics header.
            +p(ContentDesignStyles.categoryLabel(context).name, ContentDesignStyles.topicsCategorySpacing.name) {
                +localizator("md.popular").uppercase()
            }

            // Topics list.
            ul {
                popularLi(context, localizator("md.materialTheming"), endpoints.materialTheming)
                popularLi(context, localizator("md.iconography"),     endpoints.iconography)
                popularLi(context, localizator("md.textFields"),      endpoints.textFields)
            }

        }

        topicBlock(
            context,
            MdMaterialDarkThemeImage,
            localizator("md.materialDarkTheme"),
            localizator("md.foundation"),
            localizator("md.materialDarkTheme"),
            localizator("md.learnHowToDesignADarkThemeVersionOfYourMaterialUI")
        )

        topicBlock(
            context,
            MdSoundGuidelinesImage,
            localizator("md.soundGuidelines"),
            localizator("md.guidelines"),
            localizator("md.materialGuidelines"),
            localizator("md.useSoundToCommunicateInformationInWaysThatAugment")
        )

    }

}



// Private - reusable views.

private fun ChildrenBuilder.popularLi(context: Context, label: String, destinationEndpoint: String) {

    +li(ContentDesignStyles.popularListItem(context).name) {

        a {
            safeBlankHref = destinationEndpoint
            +label
        }

    }

}

private fun ChildrenBuilder.topicBlock(
    context: Context,
    illustration: Image,
    illustrationAlternativeText: String,
    categoryName: String,
    title: String,
    descriptionText: String
) {

    // To prevent the item from taking the entire height of the grid's row.
    div {

        // Block with a hover style.
        +div(SelectionStyles.hoverableWithIntensePaddedStroke(context).name) {

            // Topic's illustration.
            image(
                illustration,
                illustrationAlternativeText,
                ContentDesignStyles.widthRestrictedStrokedImage(context).name
            )

            // Category.
            +p(ContentDesignStyles.categoryLabel(context).name, ContentDesignStyles.topicCategorySpacing.name) {
                +categoryName.uppercase()
            }

            // Title.
            +p(ContentDesignStyles.topicTitle(context).name) { +title }

            // Description.
            +p(ContentDesignStyles.topicDescription(context).name) { +descriptionText }

        }

    }

}



// Private - styles.

private object ContentDesignStyles : DynamicStyleSheet() {

    val gridContainer: DynamicCssProvider<Context> by dynamicCss {
        display             = Display.grid
        gridTemplateColumns = GridTemplateColumns("2fr 1fr")
        rowGap              = StyleValues.spacing.absolute49
        padding(
            top        = StyleValues.spacing.absolute89,
            horizontal = StyleValues.spacing.absolute20,
            bottom     = StyleValues.spacing.absolute52
        )
        backgroundColor = Theme.palette.surface1(it)
    }

    val horizontalSpacingGridBlock: DynamicCssProvider<Context> by dynamicCss {
        paddingLeft  = StyleValues.spacing.absolute20
        paddingRight = StyleValues.spacing.absolute20
    }

    val bigLabel: DynamicCssProvider<Context> by dynamicCss {
        fontSize = StyleValues.fontSizes.relative3p65
        color = Theme.palette.onSurface1(it)
    }

    val captionLabel: DynamicCssProvider<Context> by dynamicCss {
        +LabelStyles.contentBlockDarkDescription(it).rules
        marginTop = StyleValues.spacing.absolute20
    }

    val categoryLabel: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.mono.rules
        fontSize = StyleValues.fontSizes.relativep85
        color = Theme.palette.onSurfaceWeaker1(it)
    }

    val topicsCategorySpacing: NamedRuleSet by css {
        marginTop = StyleValues.spacing.absolute1
    }

    val topicCategorySpacing: NamedRuleSet by css {
        marginTop = StyleValues.spacing.absolute24
    }

    val popularListItem: DynamicCssProvider<Context> by dynamicCss {
        display = Display.block
        val topSpacing = StyleValues.spacing.absolute6
        marginTop = topSpacing
        marginBottom = topSpacing + StyleValues.spacing.absolute5 // as the bottom margin interleaves with the next one
        children {
            +TransitionStyles.flashingTransition(::color).rules
            fontSize = StyleValues.fontSizes.relative1p2
            textDecoration(TextDecorationLine.underline)
            color = Theme.palette.onSurface1(it)
            hover {
                color = Theme.palette.onSurfaceFocused1(it)
            }
        }
    }

    val widthRestrictedStrokedImage: DynamicCssProvider<Context> by dynamicCss {
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
