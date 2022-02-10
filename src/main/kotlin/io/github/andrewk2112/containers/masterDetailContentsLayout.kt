package io.github.andrewk2112.containers

import io.github.andrewk2112.components.sampleLabel
import kotlinx.css.*
import react.Props
import react.fc
import styled.css
import styled.styledDiv

// FIXME: Components can be nested inside as props / children.
val masterDetailContentsLayout = fc<Props> {
    styledDiv {
        css {
            height   = 100.pct
            width    = 100.pct
            overflow = Overflow.scroll
            position = Position.relative // FIXME: do we need it?
            display  = Display.flex
        }
        // FIXME: Should be totally reorganized and renamed.
        styledDiv {
            css {
                borderRightColor = Color.red
                borderRightWidth = 2.px
                borderRightStyle = BorderStyle.solid
                width  = 300.px
                height = 100.pct
                overflow = Overflow.scroll
            }
            for (i in 0..10) {
                sampleLabel {
                    attrs.text = "test"
                }
            }
        }
    }
}
