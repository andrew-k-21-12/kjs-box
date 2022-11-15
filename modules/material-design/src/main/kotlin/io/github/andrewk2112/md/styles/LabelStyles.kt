package io.github.andrewk2112.md.styles

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
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

    val contentBlockSubTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockSubTitleSize.rules
        color = Theme.palette.onSurface2(it)
    }

    val contentBlockDarkSubTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockSubTitleSize.rules
        color = Theme.palette.onSurface1(it)
    }

    val contentBlockLinkSmallTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockLinkColor(it).rules
        fontSize = StyleValues.fontSizes.relative1
        fontWeight = FontWeight.w600
    }

    val contentBlockDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseDescription(it).rules
        fontSize = StyleValues.fontSizes.relative1
    }

    val contentBlockSmallerDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseDescription(it).rules
        fontSize = StyleValues.fontSizes.relativep95
    }

    val contentBlockDate: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.mono.rules
        fontSize = StyleValues.fontSizes.relativep875
        color = Theme.palette.onSurfaceLighter2(it)
    }



    // Private.

    private val contentBlockTitleSize: DynamicCssProvider<Context> by dynamicCss {
        fontSize   = Theme.fontSizes.adaptive4(it)
        fontWeight = FontWeight.w500
    }

    private val contentBlockSubTitleSize: NamedRuleSet by css {
        fontSize = StyleValues.fontSizes.relative1p25
    }

    private val contentBlockBaseDescription: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.light.rules
        color = Theme.palette.onSurfaceLighter2(it)
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
