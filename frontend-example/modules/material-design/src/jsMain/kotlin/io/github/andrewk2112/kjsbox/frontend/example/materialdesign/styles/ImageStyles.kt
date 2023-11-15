package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles

import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.accessors.DesignTokens
import kotlinx.css.LinearDimension
import kotlinx.css.height
import kotlinx.css.pct
import kotlinx.css.width

/**
 * Typical image and icon styles.
 */
object ImageStyles : DynamicStyleSheet() {

    val fitWidthKeepAspectImage: NamedRuleSet by css {
        width  = 100.pct
        height = LinearDimension.auto
    }

    val smallSizedIcon: NamedRuleSet by css {
        width  = DesignTokens.reference.sizes.absolute32
        height = DesignTokens.reference.sizes.absolute32
    }

}
