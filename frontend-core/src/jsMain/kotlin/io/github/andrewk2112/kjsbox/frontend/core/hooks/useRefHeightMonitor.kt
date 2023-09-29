package io.github.andrewk2112.kjsbox.frontend.core.hooks

import react.RefObject
import react.useEffectOnce
import react.useState
import web.dom.Element
import web.dom.observers.ResizeObserver

/**
 * Monitors for and notifies about the height updates of the provided [ref] via the [onHeightChanged] callback.
 */
inline fun useRefHeightMonitor(ref: RefObject<Element>, crossinline onHeightChanged: (Double) -> Unit) = useEffectOnce {
    val resizeObserver = ref.current?.let { element ->
        var previousHeight = 0.0
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

/**
 * Monitors for and notifies about the height updates of the provided [ref] via the returned state value.
 */
fun useRefHeightMonitor(ref: RefObject<Element>): Double {
    var height by useState(0.0)
    useRefHeightMonitor(ref) { height = it }
    return height
}
