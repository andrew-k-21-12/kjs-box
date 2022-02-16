package io.github.andrewk2112.containers

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.dinjection.di
import io.github.andrewk2112.redux.reducers.ContextReducer
import kotlinx.css.*
import org.kodein.di.instance
import react.Props
import react.dom.p
import react.fc
import styled.css
import styled.styledDiv

val app = fc<Props> {

    // FIXME: Themes shouldn't be singletons - reorganize them with DI!

    // FIXME: Localization and routes!

    // FIXME: Reply to SO on Linked vs ArrayList, save this and other SO articles somewhere!

    val contextReducer: ContextReducer by di.instance()

    contextReducer.useScreenSizeMonitor()
    val context = contextReducer.useSelector()

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
