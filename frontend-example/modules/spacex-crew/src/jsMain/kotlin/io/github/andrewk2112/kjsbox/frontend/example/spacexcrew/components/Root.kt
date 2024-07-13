package io.github.andrewk2112.kjsbox.frontend.example.spacexcrew.components

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useLocalizator
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.spacexcrew.TranslationLocalizationKeys.NAMESPACE
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.spacexcrew.TranslationLocalizationKeys.SEARCH_IN_THE_SPACE_X_CREW_BY_NAME_KEY
import io.github.andrewk2112.kjsbox.frontend.example.spacexcrew.viewmodels.RootViewModel
import io.github.andrewk2112.utility.coroutines.react.extensions.asReactState
import io.github.andrewk2112.utility.react.components.FC
import io.github.andrewk2112.utility.react.dom.extensions.typedTarget
import io.github.andrewk2112.utility.react.hooks.useState
import kotlinx.css.*
import react.dom.events.FormEventHandler
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.span
import react.dom.html.ReactHTML.ul
import react.useCallback
import web.html.HTMLInputElement
import web.html.InputType



// Component.

val Root by FC {

    val localizator = useLocalizator(NAMESPACE)

    // Should be created or injected only once for all rendering cycles (not as a global singleton!).
    val viewModel by useState(::RootViewModel) { it.onCleared() }
    val uiState   by viewModel.uiState.asReactState()

    val onNewNameSearchQuery: FormEventHandler<HTMLInputElement> = useCallback {
        viewModel.submitNameSearchQuery(it.typedTarget.value)
    }

    +label(RootStyles.withMargins.name) {
        htmlFor = "name-search-query"
        +localizator(SEARCH_IN_THE_SPACE_X_CREW_BY_NAME_KEY)
    }

    +input(RootStyles.withMargins.name) {
        id      = "name-search-query"
        type    = InputType.search
        onInput = onNewNameSearchQuery
    }

    ul {

        for (crewMember in uiState.foundCrewMembers) {

            +li(RootStyles.listItem.name) {
                key = crewMember.id
                +div(RootStyles.imageContainer.name) {
                    +img(RootStyles.image.name) {
                        src = crewMember.image
                        alt = crewMember.name
                    }
                }
                span { +crewMember.name }
            }

        }

    }

}



// Styles.

/**
 * All required basic styles done in a static manner for simplicity (as this is not a styling example).
 */
private object RootStyles : DynamicStyleSheet() {

    val withMargins: NamedRuleSet by css {
        margin = Margin(4.px, 4.px, 4.px, 4.px)
    }

    val listItem: NamedRuleSet by css {
        clear = Clear.left
    }

    val imageContainer: NamedRuleSet by css {
        +withMargins.rules
        width  = 200.px
        height = 200.px
        float  = Float.left
    }

    val image: NamedRuleSet by css {
        maxWidth  = 100.pct
        maxHeight = 100.pct
    }

}
