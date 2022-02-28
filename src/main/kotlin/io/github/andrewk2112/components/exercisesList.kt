package io.github.andrewk2112.components

import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.dinjection.di
import io.github.andrewk2112.hooks.useStateGetterOnce
import io.github.andrewk2112.redux.reducers.ContextReducer
import kotlinx.css.*
import org.kodein.di.direct
import org.kodein.di.instance
import react.Props
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import react.fc
import styled.css
import styled.styledDiv

val exercisesList = fc<Props> {

    val context = useStateGetterOnce { di.direct.instance<ContextReducer>() }.useSelector()

    styledDiv {

        css {
            height          = 100.pct
            width           = 100.pct
            overflow        = Overflow.scroll
            backgroundColor = Theme.palette.background1.get(context)
            fontSize        = 100.pct
            +Theme.fontFaces.main.get(context)
        }

        ul {
            li {
                sampleLabel {
                    attrs.text = "ex1"
                }
            }
            li {
                +"ex2"
            }
        }

    }

}
