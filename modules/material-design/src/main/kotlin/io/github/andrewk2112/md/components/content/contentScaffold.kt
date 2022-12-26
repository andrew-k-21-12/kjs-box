package io.github.andrewk2112.md.components.content

import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.md.components.common.horizontalDivider
import io.github.andrewk2112.md.components.content.sections.designIntro
import io.github.andrewk2112.md.components.content.sections.materialArticles
import io.github.andrewk2112.md.components.content.sections.materialStudies
import io.github.andrewk2112.md.components.content.sections.whatsNew
import io.github.andrewk2112.md.styles.LayoutStyles
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import kotlinx.css.padding
import react.ChildrenBuilder
import react.VFC
import react.dom.html.ReactHTML.div



// Components.

val contentScaffold = VFC {
    designIntro()
    whatsNew()
    divider()
    materialArticles()
    materialStudies()
}

private fun ChildrenBuilder.divider() = +div(ContentScaffoldStyles.divider.name) { horizontalDivider() }



// Styles.

private object ContentScaffoldStyles : DynamicStyleSheet() {

    val divider: NamedRuleSet by css {
        +LayoutStyles.contentContainer.rules
        padding(horizontal = StyleValues.spacing.absolute40)
    }

}
