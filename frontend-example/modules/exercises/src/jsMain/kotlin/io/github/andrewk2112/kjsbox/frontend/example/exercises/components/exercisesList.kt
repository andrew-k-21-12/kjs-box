package io.github.andrewk2112.kjsbox.frontend.example.exercises.components

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.core.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.fonts.exercises.SourceSansProFontStyles
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.exercises.materialDesignKey
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.exercises.namespace
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.exercises.toBeContinuedKey
import io.github.andrewk2112.kjsbox.frontend.core.routes.MaterialDesignRoute
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.accessors.designTokens
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useAppContext
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useLocalizator
import kotlinx.css.*
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul

// Components.

val exercisesList = FC {

    val context     = useAppContext()
    val localizator = useLocalizator(namespace)

    +div(clazz = ExercisesListStyles.container.name) {

        // The list of available exercises.
        ul {
            linkItem(context, localizator(materialDesignKey), MaterialDesignRoute.path)
            contentsItem { +localizator(toBeContinuedKey) }
        }

    }

}

private fun ChildrenBuilder.linkItem(context: Context, label: String, destinationEndpoint: String) =
    contentsItem { exerciseLink(context, label, destinationEndpoint) }

private inline fun ChildrenBuilder.contentsItem(crossinline block: ChildrenBuilder.() -> Unit) =
    +li(clazz = ExercisesListStyles.listItem.name, block)



// Styles.

private object ExercisesListStyles : DynamicStyleSheet() {

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
