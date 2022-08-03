package io.github.andrewk2112.ui.components.exercises

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.designtokens.stylesheets.NamedRuleSet
import io.github.andrewk2112.extensions.withClassName
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.routes.MaterialDesignRoute
import kotlinx.css.*
import react.*
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul

// Public.

val exercisesList = FC<Props> {

    val context     = useAppContext()
    val localizator = useLocalizator()

    withClassName(div, ExercisesListStyles.root.name) {

        ul {
            exerciseLiWithLink(context, localizator("exercises.materialDesign"), MaterialDesignRoute.path)
            exerciseLiWithContents { +localizator("exercises.toBeContinued") }
        }

    }

}



// Private - reusable views.

private inline fun ChildrenBuilder.exerciseLiWithContents(crossinline block: ChildrenBuilder.() -> Unit) =
    withClassName(li, ExercisesListStyles.listItem.name, block = block)

private fun ChildrenBuilder.exerciseLiWithLink(context: Context, label: String, destinationEndpoint: String) =
    exerciseLiWithContents { exerciseLink(context, label, destinationEndpoint) }



// Private - styles.

private object ExercisesListStyles : DynamicStyleSheet() {

    val root: NamedRuleSet by css {
        +StyleValues.fontFaces.sourceSansPro.rules
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