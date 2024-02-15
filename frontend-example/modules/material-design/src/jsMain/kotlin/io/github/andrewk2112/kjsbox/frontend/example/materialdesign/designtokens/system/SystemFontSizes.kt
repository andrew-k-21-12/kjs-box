package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system

import io.github.andrewk2112.kjsbox.frontend.designtokens.ContextedFontSizes
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context.ScreenSize.PHONE
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context.ScreenSize.SMALL_TABLET
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.DesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.reference.ReferenceFontSizes

class SystemFontSizes(
    private val designTokens: DesignTokens,
    private val fontSizes: ReferenceFontSizes
) : ContextedFontSizes<Context>() {

    val adaptive1 = get { if (screenSize <= SMALL_TABLET) fontSizes.relative0p875 else fontSizes.relative0p95 }

    val adaptive2 = get { if (screenSize > PHONE) fontSizes.relative1 else fontSizes.relative0p875 }

    val adaptive3 = get { if (screenSize == SMALL_TABLET) fontSizes.relative1p125 else fontSizes.relative1p25 }

    val adaptive4 = get {
        when {
            screenSize <= PHONE        -> designTokens.reference.fontSizes.relative1p5
            screenSize <= SMALL_TABLET -> fontSizes.relative1p75
            else                       -> designTokens.reference.fontSizes.relative2
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
