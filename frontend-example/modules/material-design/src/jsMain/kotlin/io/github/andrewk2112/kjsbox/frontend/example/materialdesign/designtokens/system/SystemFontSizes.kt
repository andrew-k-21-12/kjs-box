package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.*
import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context.ScreenSize.*
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.accessors.DesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.reference.ReferenceFontSizes

class SystemFontSizes(private val fontSizes: ReferenceFontSizes) : ContextedFontSizes<Context>() {

    val adaptive1 = get { if (screenSize <= SMALL_TABLET) fontSizes.relative0p875 else fontSizes.relative0p95 }

    val adaptive2 = get { if (screenSize > PHONE) fontSizes.relative1 else fontSizes.relative0p875 }

    val adaptive3 = get { if (screenSize == SMALL_TABLET) fontSizes.relative1p125 else fontSizes.relative1p25 }

    val adaptive4 = get {
        when {
            screenSize <= PHONE        -> DesignTokens.reference.fontSizes.relative1p5
            screenSize <= SMALL_TABLET -> fontSizes.relative1p75
            else                       -> DesignTokens.reference.fontSizes.relative2
        }
    }

    val adaptive5 = get {
        when {
            screenSize <= PHONE        -> fontSizes.relative2p5
            screenSize <= SMALL_TABLET -> fontSizes.relative3p125
            else                       -> fontSizes.relative3p75
        }
    }

}
