package io.github.andrewk2112.ui.styles

import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.designtokens.stylesheets.NamedRuleSet
import kotlinx.css.height
import kotlinx.css.width

/**
 * Contains typical icon styles.
 * */
object IconsStyles : DynamicStyleSheet() {

    val smallSizedIcon: NamedRuleSet by css {
        width  = StyleValues.sizes.absolute32
        height = StyleValues.sizes.absolute32
    }

}
