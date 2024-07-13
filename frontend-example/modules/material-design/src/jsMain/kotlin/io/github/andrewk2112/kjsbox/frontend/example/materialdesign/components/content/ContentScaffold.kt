package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.sections.DesignIntro
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.sections.MaterialArticles
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.sections.MaterialStudies
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.content.sections.WhatsNew
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.HorizontalDivider
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.useMaterialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import io.github.andrewk2112.utility.react.components.FC
import io.github.andrewk2112.utility.react.hooks.useMemoWithReferenceCount
import kotlinx.css.Padding
import kotlinx.css.padding
import react.ChildrenBuilder
import react.dom.html.ReactHTML.div



// Components.

val ContentScaffold by FC {

    val component = useMaterialDesignComponent()
    val styles    = useMemoWithReferenceCount(component) {
                        ContentScaffoldStyles(component.getMaterialDesignTokens())
                    }

    DesignIntro()
    WhatsNew()
    Divider(styles)
    MaterialArticles()
    MaterialStudies()

}

@Suppress("FunctionName")
private fun ChildrenBuilder.Divider(styles: ContentScaffoldStyles) =
    +div(clazz = styles.divider.name) { HorizontalDivider() }



// Styles.

private class ContentScaffoldStyles(
    private val materialDesignTokens: MaterialDesignTokens
) : DynamicStyleSheet(materialDesignTokens::class) {

    val divider: NamedRuleSet by css {
        +materialDesignTokens.component.layout.contentContainer.rules
        padding = Padding(horizontal = materialDesignTokens.reference.spacing.absolute40)
    }

}
