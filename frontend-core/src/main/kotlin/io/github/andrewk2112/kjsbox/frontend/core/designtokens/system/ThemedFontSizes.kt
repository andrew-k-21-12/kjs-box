package io.github.andrewk2112.kjsbox.frontend.core.designtokens.system

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context.ScreenSize.*
import io.github.andrewk2112.kjsbox.frontend.core.designtokens.StyleValues

/**
 * Provides context-based font sizes according to the current dynamic configuration.
 */
class ThemedFontSizes {

    val adaptive1: ThemedSize
        get() = {
            StyleValues.fontSizes.run { if (it.screenSize <= SMALL_TABLET) relativep875 else relativep95 }
        }

    val adaptive2: ThemedSize
        get() = {
            StyleValues.fontSizes.run { if (it.screenSize > PHONE) relative1 else relativep875 }
        }

    val adaptive3: ThemedSize
        get() = {
            StyleValues.fontSizes.run { if (it.screenSize == SMALL_TABLET) relative1p125 else relative1p25 }
        }

    val adaptive4: ThemedSize
        get() = {
            StyleValues.fontSizes.run { if (it.screenSize > PHONE) relative1p5 else relative2 }
        }

    val adaptive5: ThemedSize
        get() = {
            when {
                it.screenSize <= PHONE        -> StyleValues.fontSizes.relative1p5
                it.screenSize <= SMALL_TABLET -> StyleValues.fontSizes.relative1p75
                else                          -> StyleValues.fontSizes.relative2
            }
        }

    val adaptive6: ThemedSize
        get() = {
            when {
                it.screenSize <= PHONE        -> StyleValues.fontSizes.relative2p5
                it.screenSize <= SMALL_TABLET -> StyleValues.fontSizes.relative3p125
                else                          -> StyleValues.fontSizes.relative3p75
            }
        }

}
