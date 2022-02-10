package io.github.andrewk2112.containers

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.redux.reducers.ContextReducer
import kotlinx.css.*
import react.Props
import react.dom.button
import react.dom.onClick
import react.fc
import styled.css
import styled.styledDiv

val app = fc<Props> {

    // FIXME: Drop me!
    val contextModifier = ContextReducer.useDispatch()

    styledDiv {

        css {
            height   = 100.pct
            width    = 100.pct
            overflow = Overflow.scroll
            // FIXME: Introduce a global context!
            // backgroundColor = ThemedPalette.background1.get(Context())
            // +ThemedFontFaces.main.get()
            fontSize = 100.pct
        }

        // FIXME: Drop me!
        button {
            attrs.onClick = {
                contextModifier(ContextReducer.Action.UpdateScreenSize(Context.ScreenSize.PHONE))
                Unit
            }
            +"test"
        }

        masterDetailContentsLayout()

    }

}
