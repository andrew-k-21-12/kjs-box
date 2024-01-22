package io.github.andrewk2112.kjsbox.frontend.example.exercises.components

import io.github.andrewk2112.kjsbox.frontend.core.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.hooks.useMemoWithReferenceCount
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.useRootComponent
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.DesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.fonts.exercises.ComfortaaFontStyles
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.ChildrenBuilder
import react.router.dom.Link
import remix.run.router.To



// Public.

fun ChildrenBuilder.exerciseLink(context: Context, text: String, to: To) {

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
