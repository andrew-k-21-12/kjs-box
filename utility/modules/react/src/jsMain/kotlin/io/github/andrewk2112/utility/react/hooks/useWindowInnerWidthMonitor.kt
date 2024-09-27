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
    var resultWidth by useState { windowInnerWidth } // setting an initial value before any rendering has been performed
    useEffectOnce {
        // This local variable is needed
        // because the event listener below captures only a stale value of the state hook (its initial value).
        var currentWidth = windowInnerWidth
        // Checking and notifying if the width has been updated right after the first rendering.
        if (resultWidth != currentWidth) {
            resultWidth = currentWidth
        }
        val resizeEventListener: (Event) -> Unit = {
            val newWidth = windowInnerWidth
            if (currentWidth != newWidth) {
                currentWidth = newWidth
                resultWidth = newWidth
            }
        }
        window.addEventListener(resizeEventType, resizeEventListener)
        cleanup {
            window.removeEventListener(resizeEventType, resizeEventListener)
        }
    }
    return resultWidth
}



// Private.

/** Retrieves the current [org.w3c.dom.Window.innerWidth]. */
private val windowInnerWidth: Int inline get() = window.innerWidth

/** Target event type to listen for the updates of. */
private val resizeEventType inline get() = "resize"
