package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles

import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.accessors.MaterialDesignTokens
import kotlinx.css.boxShadow
import kotlinx.css.properties.BoxShadow
import kotlinx.css.px

/**
 * Contains typical shadow styles.
 */
object ShadowStyles : DynamicStyleSheet() {

    val defaultShadow: NamedRuleSet by css {
        boxShadow += BoxShadow(
            MaterialDesignTokens.reference.palette.blackAlpha5,
            offsetY    = MaterialDesignTokens.reference.spacing.absolute2,
            blurRadius = MaterialDesignTokens.reference.radii.shadowAbsolute4
        )
    }

    val complexShadow: NamedRuleSet by css {
        boxShadow += BoxShadow(
            MaterialDesignTokens.reference.palette.blackAlpha3,
            offsetX      = 0.px,
            offsetY      = MaterialDesignTokens.reference.spacing.absolute8,
            blurRadius   = MaterialDesignTokens.reference.radii.shadowAbsolute10,
            spreadRadius = MaterialDesignTokens.reference.radii.shadowAbsoluteN5
        )
        boxShadow += BoxShadow(
            MaterialDesignTokens.reference.palette.blackAlpha2,
            offsetX      = 0.px,
            offsetY      = MaterialDesignTokens.reference.spacing.absolute16,
            blurRadius   = MaterialDesignTokens.reference.radii.shadowAbsolute24,
            spreadRadius = MaterialDesignTokens.reference.radii.shadowAbsolute2
        )
        boxShadow += BoxShadow(
            MaterialDesignTokens.reference.palette.blackAlpha1,
            offsetX      = 0.px,
            offsetY      = MaterialDesignTokens.reference.spacing.absolute6,
            blurRadius   = MaterialDesignTokens.reference.radii.shadowAbsolute30,
            spreadRadius = MaterialDesignTokens.reference.radii.shadowAbsolute5
        )
    }

}
