package io.github.andrewk2112.ui.styles

import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.designtokens.stylesheets.NamedRuleSet
import kotlinx.css.properties.boxShadow

/**
 * Contains typical shadow styles.
 * */
object ShadowStyles : DynamicStyleSheet() {

    val defaultShadow: NamedRuleSet by css {
        boxShadow(
            StyleValues.palette.blackWithAlpha2,
            offsetY = StyleValues.spacing.absolute2,
            blurRadius = StyleValues.radii.shadowAbsolute4
        )
    }

}
