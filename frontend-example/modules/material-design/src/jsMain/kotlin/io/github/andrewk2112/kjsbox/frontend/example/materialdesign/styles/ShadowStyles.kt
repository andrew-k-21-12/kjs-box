package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles

import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.accessors.DesignTokens
import kotlinx.css.boxShadow
import kotlinx.css.properties.BoxShadow
import kotlinx.css.px

/**
 * Contains typical shadow styles.
 */
object ShadowStyles : DynamicStyleSheet() {

    val defaultShadow: NamedRuleSet by css {
        boxShadow += BoxShadow(
            DesignTokens.reference.palette.blackAlpha5,
            offsetY    = DesignTokens.reference.spacing.absolute2,
            blurRadius = DesignTokens.reference.radii.shadowAbsolute4
        )
    }

    val complexShadow: NamedRuleSet by css {
        boxShadow += BoxShadow(
            DesignTokens.reference.palette.blackAlpha3,
            offsetX      = 0.px,
            offsetY      = DesignTokens.reference.spacing.absolute8,
            blurRadius   = DesignTokens.reference.radii.shadowAbsolute10,
            spreadRadius = DesignTokens.reference.radii.shadowAbsoluteN5
        )
        boxShadow += BoxShadow(
            DesignTokens.reference.palette.blackAlpha2,
            offsetX      = 0.px,
            offsetY      = DesignTokens.reference.spacing.absolute16,
            blurRadius   = DesignTokens.reference.radii.shadowAbsolute24,
            spreadRadius = DesignTokens.reference.radii.shadowAbsolute2
        )
        boxShadow += BoxShadow(
            DesignTokens.reference.palette.blackAlpha1,
            offsetX      = 0.px,
            offsetY      = DesignTokens.reference.spacing.absolute6,
            blurRadius   = DesignTokens.reference.radii.shadowAbsolute30,
            spreadRadius = DesignTokens.reference.radii.shadowAbsolute5
        )
    }

}
