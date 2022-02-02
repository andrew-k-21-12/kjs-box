package io.github.andrewk2112.designtokens

import kotlinx.css.*
import styled.StyleSheet

/**
 * Contains end values (i.e. colors, sizes, typefaces and font weights) available in styling.
 * */
object DTReference {

    /**
     * Contains all end colors available in styling.
     * */
    object Palette {

        val white = Color("#FFFFFF")

    }

    object FontSize {

        val small = 2.em

    }

    object FontFace : StyleSheet("ReferenceDesignTokensForFontFaces", isStatic = true) {

        val sourceSansPro by css { // can be converted into a function to provide different styles of the same font
                                   // also, there is no way to refactor font names and make sure they exist
            fontFace {
                fontFamily = "SourceSansPro"
                src = "local(SourceSansPro-Regular), url(fonts/SourceSansPro-Regular.ttf) format(\"truetype\")"
                fontWeight = FontWeight.normal
                fontStyle = FontStyle.normal
            }
            fontFamily = "SourceSansPro, sans-serif"
            fontWeight = FontWeight.normal
            fontStyle = FontStyle.normal
        }

        val comfortaa by css {
            fontFace {
                fontFamily = "Comfortaa"
                src = "local(Comfortaa-Regular), url(fonts/Comfortaa-Regular.ttf) format(\"truetype\")"
                fontWeight = FontWeight.normal
                fontStyle = FontStyle.normal
            }
            fontFamily = "Comfortaa, sans-serif"
            fontWeight = FontWeight.normal
            fontStyle = FontStyle.normal
        }

    }

}
