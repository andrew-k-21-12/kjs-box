package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.accessors.materialDesignTokens
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
        fontSize = materialDesignTokens.reference.fontSizes.relative0p875
        color = materialDesignTokens.system.palette.action3(it)
        hover {
            opacity = materialDesignTokens.reference.opacities.p8
        }
    }

    val contentBlockTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockTitleSize(it).rules
        color = materialDesignTokens.system.palette.onSurface2(it)
    }

    val contentBlockLinkTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockTitleSize(it).rules
        +contentBlockLinkColor(it).rules
        textDecoration = TextDecoration.none
    }

    val contentBlockDarkTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockTitleSize(it).rules
        color = materialDesignTokens.system.palette.onSurface1(it)
    }

    val contentBlockSubTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockSubTitleSize(it).rules
        color = materialDesignTokens.system.palette.onSurface2(it)
    }

    val contentBlockDarkSubTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockSubTitleSize(it).rules
        color = materialDesignTokens.system.palette.onSurface1(it)
    }

    val contentBlockLinkSmallTitle: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.system.fontStyles.bold(it).rules
        +contentBlockLinkColor(it).rules
        fontSize = materialDesignTokens.system.fontSizes.adaptive2(it)
    }

    val contentBlockDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseDescription(it).rules
        color = materialDesignTokens.system.palette.onSurface2Lighter(it)
    }

    val contentBlockDarkDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseDescription(it).rules
        color = materialDesignTokens.system.palette.onSurface1(it)
    }

    val contentBlockSmallerDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseSmallerDescription(it).rules
        color = materialDesignTokens.system.palette.onSurface2Lighter(it)
    }

    val contentBlockDarkSmallerDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseSmallerDescription(it).rules
        color = materialDesignTokens.system.palette.onSurface1(it)
    }

    val contentBlockDate: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.system.fontStyles.mono(it).rules
        fontSize = materialDesignTokens.reference.fontSizes.relative0p875
        color = materialDesignTokens.system.palette.onSurface2Lighter(it)
    }



    // Private.

    private val contentBlockTitleSize: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.system.fontStyles.regular(it).rules
        fontSize = materialDesignTokens.system.fontSizes.adaptive4(it)
    }

    private val contentBlockSubTitleSize: DynamicCssProvider<Context> by dynamicCss {
        fontSize = materialDesignTokens.system.fontSizes.adaptive3(it)
    }

    private val contentBlockBaseDescription: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.system.fontStyles.light(it).rules
        fontSize = materialDesignTokens.system.fontSizes.adaptive2(it)
    }

    private val contentBlockBaseSmallerDescription: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.system.fontStyles.light(it).rules
        fontSize = materialDesignTokens.system.fontSizes.adaptive1(it)
    }

    private val contentBlockLinkColor: DynamicCssProvider<Context> by dynamicCss {
        color = materialDesignTokens.system.palette.onSurface2(it)
        visited {
            color = materialDesignTokens.system.palette.onSurface2Lighter(it)
        }
        hover {
            opacity = materialDesignTokens.reference.opacities.p8
        }
    }

}
