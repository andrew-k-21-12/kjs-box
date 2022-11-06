package io.github.andrewk2112.md.styles

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration

/**
 * Styles for reusable labels.
 * */
object LabelStyles : DynamicStyleSheet() {

    // Public.

    val contentBlockLinkTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockTitleSize(it).rules
        +contentBlockLinkColor(it).rules
        textDecoration = TextDecoration.none
    }

    val contentBlockTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockTitleSize(it).rules
        color = Theme.palette.onSurface2(it)
    }

    val contentBlockTitleDescription: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.light.rules
        fontSize = StyleValues.fontSizes.relative1
        color = Theme.palette.onSurfaceLighter2(it)
    }

    val contentBlockDate: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.mono.rules
        fontSize = StyleValues.fontSizes.relativep875
        color = Theme.palette.onSurfaceLighter2(it)
    }

    val contentBlockLinkSmallTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockLinkColor(it).rules
        fontSize = StyleValues.fontSizes.relative1
        fontWeight = FontWeight.w600
    }



    // Private.

    private val contentBlockTitleSize: DynamicCssProvider<Context> by dynamicCss {
        fontSize = Theme.fontSizes.adaptive4(it)
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
