package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component

import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.accessors.materialDesignTokens
import kotlinx.css.LinearDimension
import kotlinx.css.height
import kotlinx.css.pct
import kotlinx.css.width

/**
 * Reusable image and icon styles.
 *
 * Here and for similar style sheet classes names are longer (with prefixes)
 * to avoid name conflicts for generated sheets.
 */
class MaterialDesignComponentImageStyles : DynamicStyleSheet() {

    val fitWidthKeepAspectImage: NamedRuleSet by css {
        width  = 100.pct
        height = LinearDimension.auto
    }

    val smallSizedIcon: NamedRuleSet by css {
        width  = materialDesignTokens.reference.sizes.absolute32
        height = materialDesignTokens.reference.sizes.absolute32
    }

}
