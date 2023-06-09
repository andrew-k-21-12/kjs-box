package io.github.andrewk2112.kjsbox.examples.frontend.md.styles

import io.github.andrewk2112.kjsbox.examples.frontend.resourcewrappers.fonts.materialdesign.RobotoFontStyles
import io.github.andrewk2112.kjsbox.examples.frontend.resourcewrappers.fonts.materialdesign.RobotoMonoFontStyles
import io.github.andrewk2112.kjsbox.frontend.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.stylesheets.NamedRuleSet
import kotlinx.css.FontWeight
import kotlinx.css.fontWeight

/**
 * Wraps reusable font faces with their appearance configurations.
 */
object FontStyles : DynamicStyleSheet() {

    val bold: NamedRuleSet by css {
        +RobotoFontStyles.regular.rules
        fontWeight = FontWeight.w600
    }

    val regular: NamedRuleSet by css {
        +RobotoFontStyles.regular.rules
    }

    val light: NamedRuleSet by css {
        +RobotoFontStyles.light.rules
    }

    val boldMono: NamedRuleSet by css {
        +RobotoMonoFontStyles.light.rules
        fontWeight = FontWeight.w600
    }

    val mono: NamedRuleSet by css {
        +RobotoMonoFontStyles.light.rules
    }

}
