package io.github.andrewk2112.ui.components.md.content

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.designtokens.stylesheets.DynamicCssProvider
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.extensions.withClassName
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.hooks.useStateGetterOnce
import io.github.andrewk2112.resources.endpoints.MaterialDesignTopicsEndpoints
import io.github.andrewk2112.ui.styles.StrokeColor.INTENSE
import io.github.andrewk2112.ui.styles.StrokeConfigs
import io.github.andrewk2112.ui.styles.StrokeStyles
import io.github.andrewk2112.ui.styles.TransitionStyles
import kotlinx.css.*
import kotlinx.css.properties.TextDecorationLine
import kotlinx.css.properties.textDecoration
import react.ChildrenBuilder
import react.FC
import react.Props
import react.dom.html.AnchorTarget
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.picture
import react.dom.html.ReactHTML.source
import react.dom.html.ReactHTML.ul

// Public.

val contentDesign = FC<Props> {

    val context     = useAppContext()
    val localizator = useLocalizator()
    val endpoints   = useStateGetterOnce { MaterialDesignTopicsEndpoints() }

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
            withClassName(p, ContentDesignStyles.topicsHeader(context).name) { +localizator("md.popular").uppercase() }

            // Topics list.
            ul {
                popularLi(context, localizator("md.materialTheming"), endpoints.materialThemingOverview)
                popularLi(context, localizator("md.iconography"),     endpoints.productIconography)
                popularLi(context, localizator("md.textFields"),      endpoints.textFields)
            }

        }



        // TODO

        withClassName(div, ContentDesignStyles.selectableContentBlock(context).name) {

            withClassName(div, ContentDesignStyles.kek(context).name) {

                picture {
                    source {
                        type = "image/webp"
                        srcSet = MyImage.webp
                    }
                    img {
                        src = MyImage.png
                        alt = "Popa9"
                    }
                }

            }

        }

        withClassName(div, ContentDesignStyles.selectableContentBlock(context).name) {

            withClassName(div, ContentDesignStyles.kek(context).name) {

            }

        }



    }

}



// FIXME

interface ImageData {
    val webp: dynamic
    val png: dynamic
}

object MyImage : ImageData {

    override val webp: dynamic = logoWebp

    override val png: dynamic = logoPng

}

@JsModule("./images/md/material-dark-theme.png?as=webp")
@JsNonModule
private external val logoWebp: dynamic

@JsModule("./images/md/material-dark-theme.png")
@JsNonModule
private external val logoPng: dynamic



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



// Private - styles.

private object ContentDesignStyles : DynamicStyleSheet() {

    val gridContainer: DynamicCssProvider<Context> by dynamicCss {
        display             = Display.grid
        gridTemplateColumns = GridTemplateColumns("2fr 1fr")
        rowGap              = StyleValues.spacing.absolute49
        padding(
            top        = StyleValues.spacing.absolute89,
            horizontal = StyleValues.spacing.absolute20,
            bottom     = StyleValues.spacing.absolute72
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

    val topicsHeader: DynamicCssProvider<Context> by dynamicCss {
        marginTop = StyleValues.spacing.absolute1
        fontSize = StyleValues.fontSizes.relativep85
        color = Theme.palette.onSurfaceWeaker1(it)
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



    // TODO

    val kek: DynamicCssProvider<Context> by dynamicCss {
        height = StyleValues.sizes.absolute72
        backgroundColor = Color.red
    }



}
