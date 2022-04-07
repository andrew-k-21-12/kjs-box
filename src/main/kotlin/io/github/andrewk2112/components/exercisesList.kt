package io.github.andrewk2112.components

import io.github.andrewk2112.designtokens.StaticStyleSheet
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.localization.localization
import io.github.andrewk2112.routes.MaterialDesignRoute
import kotlinx.css.*
import react.Props
import react.RBuilder
import react.dom.html.ReactHTML.ul
import react.fc
import styled.css
import styled.styledDiv
import styled.styledLi

// Public.

val exercisesList = fc<Props> {

    val context     = useAppContext()
    val localizator = localization.useLocalizator()

    styledDiv {

        css {
            +Theme.fontFaces.main.get(context)
            fontSize = 100.pct
        }

        ul {
            exerciseLiWithLink(localizator("materialDesign"), MaterialDesignRoute.path)
            exerciseLiWithContents { +localizator("toBeContinued") }
        }

    }

}



// Private.

private object ExercisesListStyles : StaticStyleSheet() {

    val listItem by css {
        val margin  = StyleValues.spacing.absolute5
        marginTop   = margin
        marginLeft  = margin
        marginRight = margin
        lastChild {
            marginBottom = margin
        }
    }

}

private fun RBuilder.exerciseLiWithContents(contents: RBuilder.() -> Unit) = styledLi {
    css(ExercisesListStyles.listItem)
    contents()
}

private fun RBuilder.exerciseLiWithLink(label: String, destination: String) = exerciseLiWithContents {
    exerciseLink {
        attrs.text = label
        attrs.to   = destination
    }
}
