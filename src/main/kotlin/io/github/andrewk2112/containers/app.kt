package io.github.andrewk2112.containers

import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.dinjection.di
import io.github.andrewk2112.redux.reducers.ContextReducer
import kotlinx.css.*
import org.kodein.di.instance
import react.Props
import react.fc
import styled.css
import styled.styledDiv

val app = fc<Props> {

    // FIXME: Localization and routes!

    // FIXME: Reply to SO on Linked vs ArrayList, save this and other SO articles somewhere!

    val contextReducer: ContextReducer by di.instance()

    contextReducer.useScreenSizeMonitor()
    val context = contextReducer.useSelector()

    styledDiv {

        css {
            height   = 100.pct
            width    = 100.pct
            overflow = Overflow.scroll
            backgroundColor = Theme.palette.background1.get(context)
            +Theme.fontFaces.main.get(context)
            fontSize = 100.pct
        }

        masterDetailContentsLayout()

    }

}
