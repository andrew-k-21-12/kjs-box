package io.github.andrewk2112.md.styles

import io.github.andrewk2112.resources.fonts.md.RobotoFontStyles
import io.github.andrewk2112.resources.fonts.md.RobotoMonoFontStyles
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet

/**
 * Wraps reusable font faces with their appearance configurations.
 */
object FontStyles : DynamicStyleSheet() {

    val regular: NamedRuleSet by css {
        +RobotoFontStyles.regular.rules
    }

    val light: NamedRuleSet by css {
        +RobotoFontStyles.light.rules
    }

    val mono: NamedRuleSet by css {
        +RobotoMonoFontStyles.light.rules
    }

}
