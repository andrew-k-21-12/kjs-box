package io.github.andrewk2112.designtokens

import styled.StyleSheet

/**
 * Component tokens represent the elements and values that comprise a component,
 * such as containers, label text, icons, and states.
 * Whenever possible, component tokens should point to a system or reference token,
 * rather than hard values such as hex codes.
 * */
object DTComponent : StyleSheet("ComponentDesignTokens", isStatic = true) { // here and in other places
                                                                            // using static stylesheets
                                                                            // to make their names readable
                                                                            // and easier to debug in a browser

    val windowBackground by css {
        +DTSystem.Color.windowBackground
        +DTSystem.Positioning.windowBackground
        +DTSystem.FontSize.basicScale
        +DTSystem.Typeface.basic
    }

    val sampleLabel by css {
        +DTSystem.FontSize.sampleLabel
        +DTSystem.Typeface.sampleLabel
    }

}
