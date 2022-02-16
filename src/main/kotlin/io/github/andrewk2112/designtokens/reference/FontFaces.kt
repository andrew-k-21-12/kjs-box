package io.github.andrewk2112.designtokens.reference

import io.github.andrewk2112.designtokens.StaticStyleSheet
import kotlinx.css.*
import styled.CssHolder
import styled.injectGlobal

/**
 * All source font faces - to be used in derived design tokens.
 * */
class FontFaces : StaticStyleSheet() {

    // Public.

    val sourceSansPro: RuleSet by declareFontFace("SourceSansPro", fallbackFontFamilies = fallbackFontFamiliesSansSerif)
    val comfortaa: RuleSet     by declareFontFace("Comfortaa",     fallbackFontFamilies = fallbackFontFamiliesSansSerif)

    // This is an example of possible font variants picking.
    // To make it optimized, the global injection should be invoked only once per font file.
    // fun sourceSansPro(isBold: Boolean): RuleSet = if (isBold) sourceSansProBold else sourceSansPro



    // Private.

    /**
     * Enumerates all supported font formats with their corresponding [extension]s and CSS [formatName]s.
     * */
    private enum class FontFormat(val extension: String, val formatName: String) {
        TTF("ttf", "truetype")
    }

    /**
     * Prepares a reusable font face CSS attributes delegate,
     * makes sure that a target font is imported once in the compiled CSS.
     *
     * @param fontFamily A base name of the target font file and a font family name attribute to be referenced in CSS.
     * @param typeSuffix A suffix to describe the particular variant of the font,
     *                   will be added to the result target font file name.
     * @param fontFormat A [FontFormat] to import the target font correctly.
     * @param fontWeight A [FontWeight] descriptor for the imported font.
     * @param fontStyle  A [FontStyle] descriptor for the imported font.
     * @param fallbackFontFamilies A list of font families to be used if the target font was not loaded.
     *
     * @return A delegate providing all font-describing CSS attributes.
     * */
    private fun declareFontFace(
        fontFamily: String,
        typeSuffix: String     = "Regular",
        fontFormat: FontFormat = FontFormat.TTF,
        fontWeight: FontWeight = FontWeight.normal,
        fontStyle: FontStyle   = FontStyle.normal,
        vararg fallbackFontFamilies: String,
    ): CssHolder {

        // A font name including the suffix, but excluding the extension.
        val baseFontName = "$fontFamily${ if (typeSuffix.isBlank()) "" else "-$typeSuffix" }"

        // Importing the font in the compiled CSS.
        injectGlobal {
            fontFace {
                this.fontFamily = fontFamily
                src = "local($baseFontName), " +
                      "url($pathToFonts$baseFontName.${fontFormat.extension}) format(\"${fontFormat.formatName}\")"
                this.fontWeight = fontWeight
                this.fontStyle  = fontStyle
            }
        }

        // Describing style attributes to reference the imported font.
        return css {
            this.fontFamily = (arrayOf(fontFamily) + fallbackFontFamilies).joinToString()
            this.fontWeight = fontWeight
            this.fontStyle  = fontStyle
        }

    }



    // Configs.

    /** Where to locate fonts in resources. */
    private val pathToFonts get() = "./fonts/"

    /** Default fallback font families for serif fonts. */
    private val fallbackFontFamiliesSerif get() = arrayOf("serif")

    /** Default fallback font families for sans-serif fonts. */
    private val fallbackFontFamiliesSansSerif get() = arrayOf("sans-serif")

}
