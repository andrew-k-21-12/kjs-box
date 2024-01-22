package io.github.andrewk2112.kjsbox.frontend.example.exercises.components

import io.github.andrewk2112.kjsbox.frontend.core.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.hooks.useMemoWithReferenceCount
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.fonts.exercises.SourceSansProFontStyles
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.exercises.materialDesignKey
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.exercises.namespace
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.exercises.toBeContinuedKey
import io.github.andrewk2112.kjsbox.frontend.core.routes.MaterialDesignRoute
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useLocalizator
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.useRootComponent
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.DesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.useDesignTokensContext
import kotlinx.css.*
import react.ChildrenBuilder
import react.FC
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul



// Components.

val exercisesList = FC {

    val context     = useDesignTokensContext()
    val localizator = useLocalizator(namespace)
    val component   = useRootComponent()
    val styles      = useMemoWithReferenceCount(component) { ExercisesListStyles(component.getDesignTokens()) }

    +div(clazz = styles.container.name) {

        // The list of available exercises.
        ul {
            linkItem(context, styles, localizator(materialDesignKey), MaterialDesignRoute.path)
            contentsItem(styles) { +localizator(toBeContinuedKey) }
        }

    }

}

private fun ChildrenBuilder.linkItem(
    context: Context,
    styles: ExercisesListStyles,
    label: String,
    destinationEndpoint: String
) =
    contentsItem(styles) { exerciseLink(context, label, destinationEndpoint) }

private inline fun ChildrenBuilder.contentsItem(
    styles: ExercisesListStyles,
    crossinline block: ChildrenBuilder.() -> Unit
) =
    +li(clazz = styles.listItem.name, block)



// Styles.

private class ExercisesListStyles(private val designTokens: DesignTokens) : DynamicStyleSheet(designTokens::class) {

    val container: NamedRuleSet by css {
        +SourceSansProFontStyles.regular.rules
        fontSize = 100.pct
    }

    val listItem: NamedRuleSet by css {
        val margin  = designTokens.reference.spacing.absolute5
        marginTop   = margin
        marginLeft  = margin
        marginRight = margin
        lastChild {
            marginBottom = margin
        }
    }

}
