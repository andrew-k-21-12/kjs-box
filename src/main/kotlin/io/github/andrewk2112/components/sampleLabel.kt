package io.github.andrewk2112.components

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.dinjection.di
import io.github.andrewk2112.redux.reducers.ContextReducer
import kotlinx.css.Color
import kotlinx.css.color
import org.kodein.di.instance
import react.Props
import react.fc
import styled.css
import styled.styledP

// FIXME: This is just an example and is going to be dropped.

external interface SampleLabelProps : Props {
    var text: String
}

// FIXME: Styles for components are convenient to describe multiple states inside.

// FIXME: Use shorter RBuilders where possible (e.g. when no props are needed)!
val sampleLabel = fc<SampleLabelProps> { props ->

    // FIXME: An example of reading the global state.
    val contextReducer: ContextReducer by di.instance()
    val context = contextReducer.useSelector()

    styledP {
        // FIXME: Need a context!
        // css(ThemedFontFaces.accent.get())
        +props.text
        // FIXME: Drop me.
        css {
            color = if (context.screenSize == Context.ScreenSize.DESKTOP) Color.green else Color.red
        }
    }

}
