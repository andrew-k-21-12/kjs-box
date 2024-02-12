package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.ContextedFontStyles
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.fonts.materialdesign.RobotoFontStyles
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.fonts.materialdesign.RobotoMonoFontStyles
import io.github.andrewk2112.kjsbox.frontend.core.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.dynamicstylesheet.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import kotlinx.css.FontWeight
import kotlinx.css.fontWeight



// Public.

class SystemFontStyles : ContextedFontStyles<Context>() {

    val light    = get { RobotoFontStyles.light }
    val regular  = get { RobotoFontStyles.regular }
    val bold     = get { fontStyles.bold }

    val mono     = get { RobotoMonoFontStyles.light }
    val monoBold = get { fontStyles.monoBold }

    private val fontStyles = MaterialDesignSystemFontStyles()

}



// Private.

private class MaterialDesignSystemFontStyles : DynamicStyleSheet() {

    val bold: NamedRuleSet by css {
        +RobotoFontStyles.regular.rules
        fontWeight = FontWeight.w600
    }

    val monoBold: NamedRuleSet by css {
        +RobotoMonoFontStyles.light.rules
        fontWeight = FontWeight.w600
    }

}
