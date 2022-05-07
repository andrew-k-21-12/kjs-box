package io.github.andrewk2112.designtokens.system

import io.github.andrewk2112.designtokens.StyleValues

/**
 * Provides context-based font faces according to the current dynamic configuration.
 * */
class ThemedFontFaces {

    val main:     ThemedFontFace get() = { StyleValues.fontFaces.sourceSansPro }
    val accent:   ThemedFontFace get() = { StyleValues.fontFaces.comfortaa }
    val material: ThemedFontFace get() = { StyleValues.fontFaces.roboto }

}
