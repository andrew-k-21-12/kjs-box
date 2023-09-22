package io.github.andrewk2112.kjsbox.frontend.core.hooks

import kotlinx.browser.window
import org.w3c.dom.events.Event
import react.useEffectOnce

/**
 * Provides a hook to monitor the current window width.
 */
class WindowWidthMonitor {

    // Public.

    /**
     * Reads a window width once when mounted and each time a window resizes.
     *
     * @param onNewWidth Delivers the updated window width.
     */
    fun useWindowWidthMonitor(onNewWidth: (Int) -> Unit) {
        useEffectOnce {
            val eventListener: (Event) -> Unit = {
                onNewWidth.invoke(windowWidth)
            }
            window.addEventListener(eventType, eventListener)
            cleanup {
                window.removeEventListener(eventType, eventListener)
            }
        }
    }

    /** Current actual window width. */
    val windowWidth: Int get() = window.innerWidth



    // Private.

    /** Target event type to listen to. */
    private val eventType = "resize"

}
