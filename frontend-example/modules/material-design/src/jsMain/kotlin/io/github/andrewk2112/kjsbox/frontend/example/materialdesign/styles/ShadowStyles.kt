package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.StyleValues
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import kotlinx.css.boxShadow
import kotlinx.css.properties.BoxShadow
import kotlinx.css.px

/**
 * Contains typical shadow styles.
 */
object ShadowStyles : DynamicStyleSheet() {

    val defaultShadow: NamedRuleSet by css {
        boxShadow += BoxShadow(
            StyleValues.palette.blackWithAlpha5,
            offsetY    = StyleValues.spacing.absolute2,
            blurRadius = StyleValues.radii.shadowAbsolute4
        )
    }

    val complexShadow: NamedRuleSet by css {
        boxShadow += BoxShadow(
            StyleValues.palette.blackWithAlpha3,
            offsetX      = 0.px,
            offsetY      = StyleValues.spacing.absolute8,
            blurRadius   = StyleValues.radii.shadowAbsolute10,
            spreadRadius = StyleValues.radii.shadowAbsolutem5
        )
        boxShadow += BoxShadow(
            StyleValues.palette.blackWithAlpha2,
            offsetX      = 0.px,
            offsetY      = StyleValues.spacing.absolute16,
            blurRadius   = StyleValues.radii.shadowAbsolute24,
            spreadRadius = StyleValues.radii.shadowAbsolute2
        )
        boxShadow += BoxShadow(
            StyleValues.palette.blackWithAlpha1,
            offsetX      = 0.px,
            offsetY      = StyleValues.spacing.absolute6,
            blurRadius   = StyleValues.radii.shadowAbsolute30,
            spreadRadius = StyleValues.radii.shadowAbsolute5
        )
    }

}
