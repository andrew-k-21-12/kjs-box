package io.github.andrewk2112.utility.react.hooks

import kotlinx.browser.window
import org.w3c.dom.events.Event
import react.useEffectOnce
import react.useState



// Public.

/**
 * Reads and delivers the current [org.w3c.dom.Window.innerWidth] with updates each time the window resizes.
 */
fun useWindowInnerWidthMonitor(): Int  {
    var currentWidth by useState { windowInnerWidth }
    useEffectOnce {
        val resizeEventListener: (Event) -> Unit = {
            val newWidth = windowInnerWidth
            if (currentWidth != newWidth) {
                currentWidth = newWidth
            }
        }
        window.addEventListener(resizeEventType, resizeEventListener)
        cleanup {
            window.removeEventListener(resizeEventType, resizeEventListener)
        }
    }
    return currentWidth
}



// Private.

/** Retrieves the current [org.w3c.dom.Window.innerWidth]. */
private val windowInnerWidth: Int inline get() = window.innerWidth

/** Target event type to listen for the updates of. */
private val resizeEventType inline get() = "resize"
