package io.github.andrewk2112.components

import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.dinjection.di
import io.github.andrewk2112.hooks.useStateGetterOnce
import io.github.andrewk2112.redux.reducers.ContextReducer
import org.kodein.di.direct
import org.kodein.di.instance
import react.Props
import react.fc
import styled.css
import styled.styledP

external interface SampleLabelProps : Props {
    var text: String
}

// FIXME: Styles for components are convenient to describe multiple states inside.

val sampleLabel = fc<SampleLabelProps> { props ->

    val context = useStateGetterOnce { di.direct.instance<ContextReducer>() }.useSelector()

    styledP {
        css(Theme.fontFaces.accent.get(context))
        +props.text
    }

}
