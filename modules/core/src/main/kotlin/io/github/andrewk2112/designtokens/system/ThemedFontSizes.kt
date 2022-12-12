package io.github.andrewk2112.designtokens.system

import io.github.andrewk2112.designtokens.Context.ScreenSize.*
import io.github.andrewk2112.designtokens.StyleValues

/**
 * Provides context-based font sizes according to the current dynamic configuration.
 */
class ThemedFontSizes {

    val adaptive1: ThemedSize
        get() = {
            if (it.screenSize.equalsOrBigger(SMALL_TABLET)) {
                StyleValues.fontSizes.relative1
            } else {
                StyleValues.fontSizes.relativep875
            }
        }

    val adaptive2: ThemedSize
        get() = {
            if (it.screenSize.equalsOrBigger(SMALL_TABLET)) {
                StyleValues.fontSizes.relative1p5
            } else {
                StyleValues.fontSizes.relative2
            }
        }

}
