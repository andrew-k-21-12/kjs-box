package io.github.andrewk2112.designtokens.system

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues

/**
 * Provides context-based font sizes according to the current dynamic configuration.
 * */
class ThemedFontSizes {

    val adaptive1: ThemedFontSize get() = { StyleValues.fontSizes.relativep85 }
    val adaptive2: ThemedFontSize get() = { StyleValues.fontSizes.relative1p2 }
    val adaptive3: ThemedFontSize
        get() = {
            when (it.screenSize) {
                Context.ScreenSize.PHONE                              -> StyleValues.fontSizes.relative2
                Context.ScreenSize.TABLET, Context.ScreenSize.DESKTOP -> StyleValues.fontSizes.relative1p5
            }
        }

}
