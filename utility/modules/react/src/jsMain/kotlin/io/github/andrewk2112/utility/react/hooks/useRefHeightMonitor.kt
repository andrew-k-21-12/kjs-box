package io.github.andrewk2112.utility.react.hooks

import react.RefObject
import react.useEffectOnce
import react.useState
import web.dom.Element
import web.dom.observers.ResizeObserver



// Public.

/**
 * Monitors for and notifies about height updates of the provided [ref] via the [onHeightChanged] callback.
 */
fun useRefHeightMonitor(ref: RefObject<Element>, onHeightChanged: (Double) -> Unit) {
    useEffectOnce {
        val resizeObserver = ref.current?.let { element ->
            var previousHeight = initialHeight
            ResizeObserver { entries, _ ->
                val newHeight = entries.firstOrNull()?.contentRect?.height
                if (newHeight != null && newHeight != previousHeight) {
                    previousHeight = newHeight
                    onHeightChanged(newHeight)
                }
            }.also { it.observe(element) }
        }
        cleanup { resizeObserver?.disconnect() }
    }
}

/**
 * Monitors for and notifies about height updates of the provided [ref] via the returned state value.
 */
fun useRefHeightMonitor(ref: RefObject<Element>): Double {
    val (height, setHeight) = useState(initialHeight)
    useRefHeightMonitor(ref, setHeight::invoke)
    return height
}



// Private.

/** Default height to be implied for an element until the first resize event. */
private inline val initialHeight get() = 0.0
