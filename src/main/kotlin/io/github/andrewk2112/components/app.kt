package io.github.andrewk2112.components

import io.github.andrewk2112.designtokens.DTComponent
import react.Props
import react.fc
import styled.css
import styled.styledDiv

val app = fc<Props> {
    styledDiv {
        css {
            +DTComponent.windowBackground
        }
        +"Simple text"
        child(sampleLabel) {
            attrs {
                text = "Hello there! Дай пять!"
            }
        }
    }
}
