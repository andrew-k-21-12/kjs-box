package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.accessors.MaterialDesignTokens
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
        fontSize = MaterialDesignTokens.reference.fontSizes.relative0p875
        color = MaterialDesignTokens.system.palette.action3(it)
        hover {
            opacity = MaterialDesignTokens.reference.opacities.p8
        }
    }

    val contentBlockTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockTitleSize(it).rules
        color = MaterialDesignTokens.system.palette.onSurface2(it)
    }

    val contentBlockLinkTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockTitleSize(it).rules
        +contentBlockLinkColor(it).rules
        textDecoration = TextDecoration.none
    }

    val contentBlockDarkTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockTitleSize(it).rules
        color = MaterialDesignTokens.system.palette.onSurface1(it)
    }

    val contentBlockSubTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockSubTitleSize(it).rules
        color = MaterialDesignTokens.system.palette.onSurface2(it)
    }

    val contentBlockDarkSubTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockSubTitleSize(it).rules
        color = MaterialDesignTokens.system.palette.onSurface1(it)
    }

    val contentBlockLinkSmallTitle: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.bold.rules
        +contentBlockLinkColor(it).rules
        fontSize = MaterialDesignTokens.system.fontSizes.adaptive2(it)
    }

    val contentBlockDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseDescription(it).rules
        color = MaterialDesignTokens.system.palette.onSurface2Lighter(it)
    }

    val contentBlockDarkDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseDescription(it).rules
        color = MaterialDesignTokens.system.palette.onSurface1(it)
    }

    val contentBlockSmallerDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseSmallerDescription(it).rules
        color = MaterialDesignTokens.system.palette.onSurface2Lighter(it)
    }

    val contentBlockDarkSmallerDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseSmallerDescription(it).rules
        color = MaterialDesignTokens.system.palette.onSurface1(it)
    }

    val contentBlockDate: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.mono.rules
        fontSize = MaterialDesignTokens.reference.fontSizes.relative0p875
        color = MaterialDesignTokens.system.palette.onSurface2Lighter(it)
    }



    // Private.

    private val contentBlockTitleSize: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.regular.rules
        fontSize = MaterialDesignTokens.system.fontSizes.adaptive4(it)
    }

    private val contentBlockSubTitleSize: DynamicCssProvider<Context> by dynamicCss {
        fontSize = MaterialDesignTokens.system.fontSizes.adaptive3(it)
    }

    private val contentBlockBaseDescription: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.light.rules
        fontSize = MaterialDesignTokens.system.fontSizes.adaptive2(it)
    }

    private val contentBlockBaseSmallerDescription: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.light.rules
        fontSize = MaterialDesignTokens.system.fontSizes.adaptive1(it)
    }

    private val contentBlockLinkColor: DynamicCssProvider<Context> by dynamicCss {
        color = MaterialDesignTokens.system.palette.onSurface2(it)
        visited {
            color = MaterialDesignTokens.system.palette.onSurface2Lighter(it)
        }
        hover {
            opacity = MaterialDesignTokens.reference.opacities.p8
        }
    }

}
