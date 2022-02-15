package io.github.andrewk2112.hooks

import kotlinx.browser.window
import org.w3c.dom.events.Event
import react.useEffectOnce

/**
 * Provides a hook to monitor the current window width.
 * */
object WindowWidthMonitor {

    // Public.

    /**
     * Reads a window width once when mounted and each time a window resizes.
     *
     * @param onNewWidth Delivers the updated window width.
     * */
    fun useWindowWidthMonitor(onNewWidth: (Int) -> Unit) {
        useEffectOnce {
            onNewWidth.invoke(windowWidth)
            val eventListener: (Event) -> Unit = {
                onNewWidth.invoke(windowWidth)
            }
            window.addEventListener(EVENT_TYPE, eventListener)
            cleanup {
                window.removeEventListener(EVENT_TYPE, eventListener)
            }
        }
    }



    // Private.

    /** Current actual window width. */
    private val windowWidth: Int get() = window.innerWidth

    /** Target event type to listen to. */
    private const val EVENT_TYPE = "resize"

}
