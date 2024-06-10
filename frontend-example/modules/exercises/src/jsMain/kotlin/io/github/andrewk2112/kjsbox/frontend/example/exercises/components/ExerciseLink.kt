package io.github.andrewk2112.kjsbox.frontend.example.exercises.components

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.useRootComponent
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.DesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.fonts.exercises.ComfortaaFontStyles
import io.github.andrewk2112.utility.react.hooks.useMemoWithReferenceCount
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.ChildrenBuilder
import react.router.dom.Link
import remix.run.router.To



// Public.

@Suppress("FunctionName")
fun ChildrenBuilder.ExerciseLink(context: Context, text: String, to: To) {

    val component = useRootComponent()
    val styles    = useMemoWithReferenceCount(component) { ExerciseLinkStyles(component.getDesignTokens()) }

    +Link(clazz = styles.link(context).name) {
        this.to = to
        +text
    }

}



// Private.

private class ExerciseLinkStyles(private val designTokens: DesignTokens) : DynamicStyleSheet(designTokens::class) {

    val link: DynamicCssProvider<Context> by dynamicCss {
        +ComfortaaFontStyles.regular.rules
        fontSize       = designTokens.system.fontSizes.adaptive(it)
        overflowWrap   = OverflowWrap.breakWord
        textDecoration = TextDecoration.none
        color          = designTokens.system.palette.action(it)
        visited {
            color = designTokens.system.palette.actionDimmed(it)
        }
    }

}
