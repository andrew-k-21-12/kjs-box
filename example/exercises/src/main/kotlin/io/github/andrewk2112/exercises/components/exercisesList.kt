package io.github.andrewk2112.exercises.components

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.resourcewrappers.fonts.exercises.SourceSansProFontStyles
import io.github.andrewk2112.resourcewrappers.locales.exercises.materialDesignKey
import io.github.andrewk2112.resourcewrappers.locales.exercises.namespace
import io.github.andrewk2112.resourcewrappers.locales.exercises.toBeContinuedKey
import io.github.andrewk2112.routes.MaterialDesignRoute
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
