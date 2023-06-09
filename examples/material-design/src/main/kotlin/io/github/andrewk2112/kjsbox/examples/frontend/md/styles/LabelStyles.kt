package io.github.andrewk2112.kjsbox.examples.frontend.md.styles

import io.github.andrewk2112.kjsbox.frontend.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.designtokens.StyleValues
import io.github.andrewk2112.kjsbox.frontend.designtokens.Theme
import io.github.andrewk2112.kjsbox.frontend.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.stylesheets.DynamicStyleSheet
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.TextDecorationLine
import kotlinx.css.properties.textDecoration

/**
 * Styles for reusable labels.
 */
object LabelStyles : DynamicStyleSheet() {

    // Public.

    val link: DynamicCssProvider<Context> by dynamicCss {
        textDecoration(TextDecorationLine.underline)
        fontSize = StyleValues.fontSizes.relativep875
        color = Theme.palette.action4(it)
        hover {
            opacity = StyleValues.opacities.p8
        }
    }

    val contentBlockTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockTitleSize(it).rules
        color = Theme.palette.onSurface2(it)
    }

    val contentBlockLinkTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockTitleSize(it).rules
        +contentBlockLinkColor(it).rules
        textDecoration = TextDecoration.none
    }

    val contentBlockDarkTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockTitleSize(it).rules
        color = Theme.palette.onSurface1(it)
    }

    val contentBlockSubTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockSubTitleSize(it).rules
        color = Theme.palette.onSurface2(it)
    }

    val contentBlockDarkSubTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockSubTitleSize(it).rules
        color = Theme.palette.onSurface1(it)
    }

    val contentBlockLinkSmallTitle: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.bold.rules
        +contentBlockLinkColor(it).rules
        fontSize = Theme.fontSizes.adaptive2(it)
    }

    val contentBlockDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseDescription(it).rules
        color = Theme.palette.onSurfaceLighter2(it)
    }

    val contentBlockDarkDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseDescription(it).rules
        color = Theme.palette.onSurface1(it)
    }

    val contentBlockSmallerDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseSmallerDescription(it).rules
        color = Theme.palette.onSurfaceLighter2(it)
    }

    val contentBlockDarkSmallerDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseSmallerDescription(it).rules
        color = Theme.palette.onSurface1(it)
    }

    val contentBlockDate: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.mono.rules
        fontSize = StyleValues.fontSizes.relativep875
        color = Theme.palette.onSurfaceLighter2(it)
    }



    // Private.

    private val contentBlockTitleSize: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.regular.rules
        fontSize = Theme.fontSizes.adaptive5(it)
    }

    private val contentBlockSubTitleSize: DynamicCssProvider<Context> by dynamicCss {
        fontSize = Theme.fontSizes.adaptive3(it)
    }

    private val contentBlockBaseDescription: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.light.rules
        fontSize = Theme.fontSizes.adaptive2(it)
    }

    private val contentBlockBaseSmallerDescription: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.light.rules
        fontSize = Theme.fontSizes.adaptive1(it)
    }

    private val contentBlockLinkColor: DynamicCssProvider<Context> by dynamicCss {
        color = Theme.palette.onSurface2(it)
        visited {
            color = Theme.palette.onSurfaceLighter2(it)
        }
        hover {
            opacity = StyleValues.opacities.p8
        }
    }

}
