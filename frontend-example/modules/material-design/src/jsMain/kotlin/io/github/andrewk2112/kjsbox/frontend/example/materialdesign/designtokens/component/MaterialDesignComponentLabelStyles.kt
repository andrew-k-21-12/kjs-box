package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.reference.ReferenceFontSizes
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.reference.ReferenceOpacities
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system.SystemFontSizes
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system.SystemFontStyles
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system.SystemPalette
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.TextDecorationLine

/**
 * Reusable label styles.
 */
class MaterialDesignComponentLabelStyles(
    private val referenceFontSizes: ReferenceFontSizes,
    private val referenceOpacities: ReferenceOpacities,
    private val systemFontSizes: SystemFontSizes,
    private val systemFontStyles: SystemFontStyles,
    private val systemPalette: SystemPalette,
) : DynamicStyleSheet() {

    // Public.

    val link: DynamicCssProvider<Context> by dynamicCss {
        textDecoration = TextDecoration(setOf(TextDecorationLine.underline))
        fontSize = referenceFontSizes.relative0p875
        color = systemPalette.action3(it)
        hover {
            opacity = referenceOpacities.p8
        }
    }

    val contentBlockTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockTitleSize(it).rules
        color = systemPalette.onSurface2(it)
    }

    val contentBlockLinkTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockTitleSize(it).rules
        +contentBlockLinkColor(it).rules
        textDecoration = TextDecoration.none
    }

    val contentBlockDarkTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockTitleSize(it).rules
        color = systemPalette.onSurface1(it)
    }

    val contentBlockSubTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockSubTitleSize(it).rules
        color = systemPalette.onSurface2(it)
    }

    val contentBlockDarkSubTitle: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockSubTitleSize(it).rules
        color = systemPalette.onSurface1(it)
    }

    val contentBlockLinkSmallTitle: DynamicCssProvider<Context> by dynamicCss {
        +systemFontStyles.bold(it).rules
        +contentBlockLinkColor(it).rules
        fontSize = systemFontSizes.adaptive2(it)
    }

    val contentBlockDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseDescription(it).rules
        color = systemPalette.onSurface2Lighter(it)
    }

    val contentBlockDarkDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseDescription(it).rules
        color = systemPalette.onSurface1(it)
    }

    val contentBlockSmallerDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseSmallerDescription(it).rules
        color = systemPalette.onSurface2Lighter(it)
    }

    val contentBlockDarkSmallerDescription: DynamicCssProvider<Context> by dynamicCss {
        +contentBlockBaseSmallerDescription(it).rules
        color = systemPalette.onSurface1(it)
    }

    val contentBlockDate: DynamicCssProvider<Context> by dynamicCss {
        +systemFontStyles.mono(it).rules
        fontSize = referenceFontSizes.relative0p875
        color = systemPalette.onSurface2Lighter(it)
    }



    // Private.

    private val contentBlockTitleSize: DynamicCssProvider<Context> by dynamicCss {
        +systemFontStyles.regular(it).rules
        fontSize = systemFontSizes.adaptive4(it)
    }

    private val contentBlockSubTitleSize: DynamicCssProvider<Context> by dynamicCss {
        fontSize = systemFontSizes.adaptive3(it)
    }

    private val contentBlockBaseDescription: DynamicCssProvider<Context> by dynamicCss {
        +systemFontStyles.light(it).rules
        fontSize = systemFontSizes.adaptive2(it)
    }

    private val contentBlockBaseSmallerDescription: DynamicCssProvider<Context> by dynamicCss {
        +systemFontStyles.light(it).rules
        fontSize = systemFontSizes.adaptive1(it)
    }

    private val contentBlockLinkColor: DynamicCssProvider<Context> by dynamicCss {
        color = systemPalette.onSurface2(it)
        visited {
            color = systemPalette.onSurface2Lighter(it)
        }
        hover {
            opacity = referenceOpacities.p8
        }
    }

}
