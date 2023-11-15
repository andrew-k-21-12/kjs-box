package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.accessors.DesignTokens
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.TextDecorationLine

/**
 * Styles for reusable labels.
 */
object LabelStyles : DynamicStyleSheet() {

    // Public.

    val link: DynamicCssProvider<Context> by dynamicCss {
        textDecoration = TextDecoration(setOf(TextDecorationLine.underline))
        fontSize = DesignTokens.reference.fontSizes.relative0p875
        color = DesignTokens.system.palette.action4(it)
        hover {
            opacity = DesignTokens.reference.opacities.p8
        }
    }

    val contentBlockTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockTitleSize(it).rules
        color = DesignTokens.system.palette.onSurface2(it)
    }

    val contentBlockLinkTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockTitleSize(it).rules
        +contentBlockLinkColor(it).rules
        textDecoration = TextDecoration.none
    }

    val contentBlockDarkTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockTitleSize(it).rules
        color = DesignTokens.system.palette.onSurface1(it)
    }

    val contentBlockSubTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockSubTitleSize(it).rules
        color = DesignTokens.system.palette.onSurface2(it)
    }

    val contentBlockDarkSubTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockSubTitleSize(it).rules
        color = DesignTokens.system.palette.onSurface1(it)
    }

    val contentBlockLinkSmallTitle: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.bold.rules
        +contentBlockLinkColor(it).rules
        fontSize = DesignTokens.system.fontSizes.adaptive2(it)
    }

    val contentBlockDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseDescription(it).rules
        color = DesignTokens.system.palette.onSurface2Lighter(it)
    }

    val contentBlockDarkDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseDescription(it).rules
        color = DesignTokens.system.palette.onSurface1(it)
    }

    val contentBlockSmallerDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseSmallerDescription(it).rules
        color = DesignTokens.system.palette.onSurface2Lighter(it)
    }

    val contentBlockDarkSmallerDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseSmallerDescription(it).rules
        color = DesignTokens.system.palette.onSurface1(it)
    }

    val contentBlockDate: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.mono.rules
        fontSize = DesignTokens.reference.fontSizes.relative0p875
        color = DesignTokens.system.palette.onSurface2Lighter(it)
    }



    // Private.

    private val contentBlockTitleSize: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.regular.rules
        fontSize = DesignTokens.system.fontSizes.adaptive5(it)
    }

    private val contentBlockSubTitleSize: DynamicCssProvider<Context> by dynamicCss {
        fontSize = DesignTokens.system.fontSizes.adaptive3(it)
    }

    private val contentBlockBaseDescription: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.light.rules
        fontSize = DesignTokens.system.fontSizes.adaptive2(it)
    }

    private val contentBlockBaseSmallerDescription: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.light.rules
        fontSize = DesignTokens.system.fontSizes.adaptive1(it)
    }

    private val contentBlockLinkColor: DynamicCssProvider<Context> by dynamicCss {
        color = DesignTokens.system.palette.onSurface2(it)
        visited {
            color = DesignTokens.system.palette.onSurface2Lighter(it)
        }
        hover {
            opacity = DesignTokens.reference.opacities.p8
        }
    }

}
