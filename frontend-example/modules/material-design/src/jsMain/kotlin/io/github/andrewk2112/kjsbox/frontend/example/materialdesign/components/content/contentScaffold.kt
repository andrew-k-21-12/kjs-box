package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.StyleValues
import io.github.andrewk2112.kjsbox.frontend.core.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.horizontalDivider
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.sections.designIntro
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.sections.materialArticles
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.sections.materialStudies
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.sections.whatsNew
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles.LayoutStyles
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import kotlinx.css.Padding
import kotlinx.css.padding
import react.ChildrenBuilder
import react.FC
import react.dom.html.ReactHTML.div



// Components.

val contentScaffold = FC {
    designIntro()
    whatsNew()
    divider()
    materialArticles()
    materialStudies()
}

private fun ChildrenBuilder.divider() = +div(clazz = ContentScaffoldStyles.divider.name) { horizontalDivider() }



// Styles.

private object ContentScaffoldStyles : DynamicStyleSheet() {

    val divider: NamedRuleSet by css {
        +LayoutStyles.contentContainer.rules
        padding = Padding(horizontal = StyleValues.spacing.absolute40)
    }

}
