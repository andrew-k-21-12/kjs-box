package io.github.andrewk2112.containers.exercises

import io.github.andrewk2112.components.exercises.exerciseLink
import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.designtokens.stylesheets.DynamicCssProvider
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.designtokens.stylesheets.NamedRuleSet
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.localization.localization
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
    val localizator = localization.useLocalizator()

    div(ExercisesListStyles.root(context).name) {

        ul {
            exerciseLiWithLink(localizator("exercises.materialDesign"), MaterialDesignRoute.path)
            exerciseLiWithContents { +localizator("exercises.toBeContinued") }
        }

    }

}



// Private.

private object ExercisesListStyles : DynamicStyleSheet() {

    val root: DynamicCssProvider<Context> by dynamicCss {
        +Theme.fontFaces.main(it).rules
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

private fun RBuilder.exerciseLiWithContents(contents: RBuilder.() -> Unit) = li(ExercisesListStyles.listItem.name) {
    contents()
}

private fun RBuilder.exerciseLiWithLink(label: String, destination: String) = exerciseLiWithContents {
    exerciseLink {
        attrs.text = label
        attrs.to   = destination
    }
}
