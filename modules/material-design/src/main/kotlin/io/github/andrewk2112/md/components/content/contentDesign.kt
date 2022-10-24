package io.github.andrewk2112.md.components.content

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import io.github.andrewk2112.extensions.withClassName
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.md.resources.endpoints.MaterialDesignTopicsEndpoints
import io.github.andrewk2112.resources.images.Image
import io.github.andrewk2112.resources.images.MdMaterialDarkThemeImage
import io.github.andrewk2112.resources.images.MdSoundGuidelinesImage
import io.github.andrewk2112.components.image
import io.github.andrewk2112.md.styles.StrokeColor.INTENSE
import io.github.andrewk2112.md.styles.StrokeConfigs
import io.github.andrewk2112.md.styles.StrokeStyles
import io.github.andrewk2112.md.styles.TransitionStyles
import kotlinx.css.*
import kotlinx.css.properties.TextDecorationLine
import kotlinx.css.properties.textDecoration
import react.ChildrenBuilder
import react.FC
import react.Props
import react.dom.html.AnchorTarget
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.ul
import react.useState

// Public.

val contentDesign = FC<Props> {

    val context     = useAppContext()
    val localizator = useLocalizator()
    val endpoints by useState { MaterialDesignTopicsEndpoints() }

    // A grid with contents.
    withClassName(div, ContentDesignStyles.gridContainer(context).name) {

        // Block with a title and caption.
        withClassName(div, ContentDesignStyles.horizontalSpacingGridBlock(context).name) {

            // Title.
            withClassName(p, ContentDesignStyles.bigLabel(context).name) { +localizator("md.design") }

            // Caption.
            withClassName(p, ContentDesignStyles.captionLabel(context).name) {
                +localizator("md.createIntuitiveAndBeautifulProductsWithMaterialDesign")
            }

        }

        // Block with a popular topics list.
        withClassName(div, ContentDesignStyles.horizontalSpacingGridBlock(context).name) {

            // Topics header.
            withClassName(
                p,
                ContentDesignStyles.categoryLabel(context).name,
                ContentDesignStyles.topicsCategorySpacing.name
            ) {
                +localizator("md.popular").uppercase()
            }

            // Topics list.
            ul {
                popularLi(context, localizator("md.materialTheming"), endpoints.materialThemingOverview)
                popularLi(context, localizator("md.iconography"),     endpoints.productIconography)
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

    withClassName(li, ContentDesignStyles.popularListItem(context).name) {

        a {

            target = AnchorTarget._blank
            href   = destinationEndpoint

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

    // Takes the entire grid height.
    div {

        // Block with a hover style.
        withClassName(div, ContentDesignStyles.selectableContentBlock(context).name) {

            // Topic's illustration.
            image(
                illustration,
                illustrationAlternativeText,
                ContentDesignStyles.widthRestrictedStrokedImage(context).name
            )

            // Category.
            withClassName(
                p,
                ContentDesignStyles.categoryLabel(context).name,
                ContentDesignStyles.topicCategorySpacing.name
            ) {
                +categoryName.uppercase()
            }

            // Title.
            withClassName(p, ContentDesignStyles.topicTitle(context).name) { +title }

            // Description.
            withClassName(p, ContentDesignStyles.topicDescription(context).name) { +descriptionText }

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
        fontSize = Theme.fontSizes.adaptive4(it)
        color = Theme.palette.onSurface1(it)
    }

    val captionLabel: DynamicCssProvider<Context> by dynamicCss {
        marginTop = StyleValues.spacing.absolute20
        fontSize = StyleValues.fontSizes.relative1
        color = Theme.palette.onSurface1(it)
    }

    val categoryLabel: DynamicCssProvider<Context> by dynamicCss {
        fontSize = StyleValues.fontSizes.relativep85
        color = Theme.palette.onSurfaceWeaker1(it)
    }

    val topicsCategorySpacing: NamedRuleSet by css {
        marginTop = StyleValues.spacing.absolute1
    }

    val topicCategorySpacing: NamedRuleSet by css {
        marginTop = StyleValues.spacing.absolute23
    }

    val popularListItem: DynamicCssProvider<Context> by dynamicCss {
        display = Display.block
        val topSpacing = StyleValues.spacing.absolute6
        marginTop = topSpacing
        marginBottom = topSpacing + StyleValues.spacing.absolute5 // as the bottom margin interleaves with the next one
        children {
            +TransitionStyles.defaultTransition(::color).rules
            fontSize = StyleValues.fontSizes.relative1p2
            textDecoration(TextDecorationLine.underline)
            color = Theme.palette.onSurface1(it)
            hover {
                color = Theme.palette.onSurfaceFocused1(it)
            }
        }
    }

    val selectableContentBlock: DynamicCssProvider<Context> by dynamicCss {
        padding(StyleValues.spacing.absolute20)
        cursor = Cursor.pointer
        hover {
            +StrokeStyles.outlineStroke(StrokeConfigs(it, INTENSE)).rules
        }
    }

    val widthRestrictedStrokedImage: DynamicCssProvider<Context> by dynamicCss {
        +StrokeStyles.outlineStroke(StrokeConfigs(it, INTENSE)).rules
        width  = 100.pct
        height = LinearDimension.auto
    }

    val topicTitle: DynamicCssProvider<Context> by dynamicCss {
        marginTop = StyleValues.spacing.absolute6
        fontSize = StyleValues.fontSizes.relative1p25
        color = Theme.palette.onSurface1(it)
    }

    val topicDescription: DynamicCssProvider<Context> by dynamicCss {
        marginTop = StyleValues.spacing.absolute11
        fontSize = StyleValues.fontSizes.relativep95
        color = Theme.palette.onSurface1(it)
    }

}
