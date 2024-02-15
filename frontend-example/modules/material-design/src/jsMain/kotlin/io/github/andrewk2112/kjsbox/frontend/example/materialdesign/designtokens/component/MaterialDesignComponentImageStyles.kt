package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.reference.ReferenceSizes
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
class MaterialDesignComponentImageStyles(private val referenceSizes: ReferenceSizes) : DynamicStyleSheet() {

    val fitWidthKeepAspectImage: NamedRuleSet by css {
        width  = 100.pct
        height = LinearDimension.auto
    }

    val smallSizedIcon: NamedRuleSet by css {
        width  = referenceSizes.absolute32
        height = referenceSizes.absolute32
    }

}
