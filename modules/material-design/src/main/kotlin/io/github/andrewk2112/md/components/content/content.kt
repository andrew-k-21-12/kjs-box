package io.github.andrewk2112.md.components.content

import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.md.components.common.horizontalDivider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import kotlinx.css.margin
import react.FC
import react.Props

// Public.

val content = FC<Props> {

    contentDesign {}
    contentWhatsNew {}
    +horizontalDivider(ContentStyles.divider.name)
    contentMaterialArticles {}
    contentMaterialStudies {}

}



// Private.

private object ContentStyles : DynamicStyleSheet() {

    val divider: NamedRuleSet by css {
        margin(horizontal = StyleValues.spacing.absolute40)
    }

}
