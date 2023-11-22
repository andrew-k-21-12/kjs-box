package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles

import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.accessors.materialDesignTokens
import kotlinx.css.boxShadow
import kotlinx.css.properties.BoxShadow
import kotlinx.css.px

/**
 * Contains typical shadow styles.
 */
object ShadowStyles : DynamicStyleSheet() {

    val defaultShadow: NamedRuleSet by css {
        boxShadow += BoxShadow(
            materialDesignTokens.reference.palette.blackAlpha5,
            offsetY    = materialDesignTokens.reference.spacing.absolute2,
            blurRadius = materialDesignTokens.reference.radii.shadowAbsolute4
        )
    }

    val complexShadow: NamedRuleSet by css {
        boxShadow += BoxShadow(
            materialDesignTokens.reference.palette.blackAlpha3,
            offsetX      = 0.px,
            offsetY      = materialDesignTokens.reference.spacing.absolute8,
            blurRadius   = materialDesignTokens.reference.radii.shadowAbsolute10,
            spreadRadius = materialDesignTokens.reference.radii.shadowAbsoluteN5
        )
        boxShadow += BoxShadow(
            materialDesignTokens.reference.palette.blackAlpha2,
            offsetX      = 0.px,
            offsetY      = materialDesignTokens.reference.spacing.absolute16,
            blurRadius   = materialDesignTokens.reference.radii.shadowAbsolute24,
            spreadRadius = materialDesignTokens.reference.radii.shadowAbsolute2
        )
        boxShadow += BoxShadow(
            materialDesignTokens.reference.palette.blackAlpha1,
            offsetX      = 0.px,
            offsetY      = materialDesignTokens.reference.spacing.absolute6,
            blurRadius   = materialDesignTokens.reference.radii.shadowAbsolute30,
            spreadRadius = materialDesignTokens.reference.radii.shadowAbsolute5
        )
    }

}
