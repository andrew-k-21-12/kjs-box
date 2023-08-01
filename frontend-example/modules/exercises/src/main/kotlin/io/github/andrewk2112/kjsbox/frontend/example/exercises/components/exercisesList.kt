package io.github.andrewk2112.kjsbox.frontend.example.exercises.components

import io.github.andrewk2112.kjsbox.frontend.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.designtokens.StyleValues
import io.github.andrewk2112.kjsbox.frontend.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.hooks.useAppContext
import io.github.andrewk2112.kjsbox.frontend.hooks.useLocalizator
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.fonts.exercises.SourceSansProFontStyles
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.exercises.materialDesignKey
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.exercises.namespace
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.exercises.toBeContinuedKey
import io.github.andrewk2112.kjsbox.frontend.routes.MaterialDesignRoute
import kotlinx.css.*
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul

// Components.

val exercisesList = VFC {

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
        val margin  = StyleValues.spacing.absolute5
        marginTop   = margin
        marginLeft  = margin
        marginRight = margin
        lastChild {
            marginBottom = margin
        }
    }

}
