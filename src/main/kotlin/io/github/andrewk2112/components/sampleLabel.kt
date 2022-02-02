package io.github.andrewk2112.components

import io.github.andrewk2112.designtokens.DTComponent
import react.Props
import react.fc
import styled.css
import styled.styledP

external interface SampleLabelProps : Props {
    var text: String
}

val sampleLabel = fc<SampleLabelProps> { props ->
    styledP {
        css {
            +DTComponent.sampleLabel
        }
        +props.text
    }
}
