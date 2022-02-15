package io.github.andrewk2112.containers

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.redux.reducers.ContextReducer
import kotlinx.css.*
import react.Props
import react.dom.p
import react.fc
import styled.css
import styled.styledDiv

val app = fc<Props> {

    // FIXME: Themes, hooks and reducers shouldn't be singletons - introduce some DI!

    // FIXME: Reply to SO on Linked vs ArrayList, save this and other SO articles somewhere!

    ContextReducer.useScreenSizeMonitor()
    val context = ContextReducer.useSelector()

    // FIXME: Drop this example.
    p {
        +when (context.screenSize) {
            Context.ScreenSize.DESKTOP -> "DESKTOP"
            Context.ScreenSize.TABLET  -> "TABLET"
            Context.ScreenSize.PHONE   -> "PHONE"
        }
    }

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

        masterDetailContentsLayout()

    }

}
