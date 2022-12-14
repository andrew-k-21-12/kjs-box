package io.github.andrewk2112.md.components.content

import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.md.components.common.horizontalDivider
import io.github.andrewk2112.md.styles.LayoutStyles
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import kotlinx.css.padding
import react.VFC
import react.dom.html.ReactHTML.div

// Public.

val content = VFC {

    contentDesign {}
    contentWhatsNew {}
    +div(ContentStyles.dividerPositioning.name) {
        horizontalDivider {}
    }
    contentMaterialArticles {}
    contentMaterialStudies {}

}



// Private.

private object ContentStyles : DynamicStyleSheet() {

    val dividerPositioning: NamedRuleSet by css {
        +LayoutStyles.contentContainer.rules
        padding(horizontal = StyleValues.spacing.absolute40)
    }

}
