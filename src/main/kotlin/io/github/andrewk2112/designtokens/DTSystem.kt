package io.github.andrewk2112.designtokens

import kotlinx.css.*
import styled.StyleSheet

/**
 * Systematizes the design language for a specific theme or context.
 * System tokens define the purpose that a reference token serves in the UI.
 * When applying theming, a system token can point to different reference tokens depending on the context,
 * such as a light or dark theme.
 * Whenever possible, system tokens should point to reference tokens rather than static values.
 * */
object DTSystem {

    object Positioning : StyleSheet("SystemDesignTokensForPositioning", isStatic = true) {

        val windowBackground by css {
            height   = 100.vh
            overflow = Overflow.scroll
        }

    }

    object Color : StyleSheet("SystemDesignTokensForColors", isStatic = true) {

        val windowBackground by css {
            backgroundColor = DTReference.Palette.white
        }

    }

    object FontSize : StyleSheet("SystemDesignTokensForFontSizes", isStatic = true) {

        val basicScale by css {
            fontSize = 100.pct
        }

        val sampleLabel by css {
            fontSize = DTReference.FontSize.small
        }

    }

    object Typeface : StyleSheet("SystemDesignTokensForTypefaces", isStatic = true) {

        val basic by css {
            +DTReference.FontFace.sourceSansPro
        }

        val sampleLabel by css {
            +DTReference.FontFace.comfortaa
        }

    }

}
