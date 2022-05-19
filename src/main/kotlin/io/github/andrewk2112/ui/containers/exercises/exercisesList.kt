package io.github.andrewk2112.ui.containers.exercises

import io.github.andrewk2112.ui.components.exercises.exerciseLink
import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.designtokens.stylesheets.NamedRuleSet
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.routes.MaterialDesignRoute
import kotlinx.css.*
import react.Props
import react.RBuilder
import react.dom.div
import react.dom.html.ReactHTML.ul
import react.dom.li
import react.fc

// Public.

val exercisesList = fc<Props> {

    val context     = useAppContext()
    val localizator = useLocalizator()

    div(ExercisesListStyles.root.name) {

        ul {
            exerciseLiWithLink(context, localizator("exercises.materialDesign"), MaterialDesignRoute.path)
            exerciseLiWithContents { +localizator("exercises.toBeContinued") }
        }

    }

}



// Private.

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

private fun RBuilder.exerciseLiWithContents(contents: RBuilder.() -> Unit) =
    li(ExercisesListStyles.listItem.name) { contents() }

private fun RBuilder.exerciseLiWithLink(context: Context, label: String, destination: String) =
    exerciseLiWithContents { exerciseLink(context, label, destination) }
