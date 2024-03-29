package io.github.andrewk2112.utility.react.dom.extensions

import react.dom.events.MouseEvent

/** Whether the left button is down. */
val MouseEvent<*, *>.isLeftButton: Boolean
    get() = button == 0
