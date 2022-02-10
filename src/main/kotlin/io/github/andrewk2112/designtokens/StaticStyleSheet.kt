package io.github.andrewk2112.designtokens

import styled.StyleSheet

/**
 * A shortcut to declare a static stylesheet (their names are readable and easier to debug in a browser)
 * with a name matching the class name.
 * */
abstract class StaticStyleSheet : StyleSheet("", isStatic = true) {

    // Using a simple class name to name a stylesheet for now.
    init {
        name = this::class.simpleName ?: ""
    }

}
