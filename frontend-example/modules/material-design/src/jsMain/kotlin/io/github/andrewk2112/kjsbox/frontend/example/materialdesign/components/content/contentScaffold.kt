package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content

import io.github.andrewk2112.kjsbox.frontend.core.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.hooks.useMemoWithReferenceCount
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.horizontalDivider
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.sections.designIntro
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.sections.materialArticles
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.sections.materialStudies
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.sections.whatsNew
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.materialDesignComponentContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import kotlinx.css.Padding
import kotlinx.css.padding
import react.ChildrenBuilder
import react.FC
import react.dom.html.ReactHTML.div
import react.useContext



// Components.

val contentScaffold = FC {

    val component = useContext(materialDesignComponentContext)
    val styles    = useMemoWithReferenceCount(component) {
                        ContentScaffoldStyles(component.getMaterialDesignTokens())
                    }

    designIntro()
    whatsNew()
    divider(styles)
    materialArticles()
    materialStudies()

}

private inline fun ChildrenBuilder.divider(styles: ContentScaffoldStyles) =
    +div(clazz = styles.divider.name) { horizontalDivider() }



// Styles.

private class ContentScaffoldStyles(
    private val materialDesignTokens: MaterialDesignTokens
) : DynamicStyleSheet(materialDesignTokens::class) {

    val divider: NamedRuleSet by css {
        +materialDesignTokens.component.layout.contentContainer.rules
        padding = Padding(horizontal = materialDesignTokens.reference.spacing.absolute40)
    }

}
