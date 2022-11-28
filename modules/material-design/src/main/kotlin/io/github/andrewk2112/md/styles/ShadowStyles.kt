package io.github.andrewk2112.md.styles

import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import kotlinx.css.properties.boxShadow
import kotlinx.css.px

/**
 * Contains typical shadow styles.
 */
object ShadowStyles : DynamicStyleSheet() {

    val defaultShadow: NamedRuleSet by css {
        boxShadow(
            StyleValues.palette.blackWithAlpha5,
            offsetY = StyleValues.spacing.absolute2,
            blurRadius = StyleValues.radii.shadowAbsolute4
        )
    }

    val complexShadow: NamedRuleSet by css {
        boxShadow(
            StyleValues.palette.blackWithAlpha3,
            0.px,
            StyleValues.spacing.absolute8,
            StyleValues.radii.shadowAbsolute10,
            StyleValues.radii.shadowAbsolutem5
        )
        boxShadow(
            StyleValues.palette.blackWithAlpha2,
            0.px,
            StyleValues.spacing.absolute16,
            StyleValues.radii.shadowAbsolute24,
            StyleValues.radii.shadowAbsolute2
        )
        boxShadow(
            StyleValues.palette.blackWithAlpha1,
            0.px,
            StyleValues.spacing.absolute6,
            StyleValues.radii.shadowAbsolute30,
            StyleValues.radii.shadowAbsolute5
        )
    }

}
