package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.reference.ReferencePalette
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.reference.ReferenceRadii
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.reference.ReferenceSpacing
import kotlinx.css.boxShadow
import kotlinx.css.properties.BoxShadow
import kotlinx.css.px

/**
 * Reusable shadow styles.
 */
class MaterialDesignComponentShadowStyles(
    private val referencePalette: ReferencePalette,
    private val referenceRadii: ReferenceRadii,
    private val referenceSpacing: ReferenceSpacing,
) : DynamicStyleSheet() {

    val default: NamedRuleSet by css {
        boxShadow += BoxShadow(
            referencePalette.blackAlpha5,
            offsetY    = referenceSpacing.absolute2,
            blurRadius = referenceRadii.shadowAbsolute4
        )
    }

    val complex: NamedRuleSet by css {
        boxShadow += BoxShadow(
            referencePalette.blackAlpha3,
            offsetX      = 0.px,
            offsetY      = referenceSpacing.absolute8,
            blurRadius   = referenceRadii.shadowAbsolute10,
            spreadRadius = referenceRadii.shadowAbsoluteN5
        )
        boxShadow += BoxShadow(
            referencePalette.blackAlpha2,
            offsetX      = 0.px,
            offsetY      = referenceSpacing.absolute16,
            blurRadius   = referenceRadii.shadowAbsolute24,
            spreadRadius = referenceRadii.shadowAbsolute2
        )
        boxShadow += BoxShadow(
            referencePalette.blackAlpha1,
            offsetX      = 0.px,
            offsetY      = referenceSpacing.absolute6,
            blurRadius   = referenceRadii.shadowAbsolute30,
            spreadRadius = referenceRadii.shadowAbsolute5
        )
    }

}
