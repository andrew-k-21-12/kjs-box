package io.github.andrewk2112.hooks

import org.w3c.dom.Element
import org.w3c.dom.ResizeObserver
import react.RefObject
import react.useEffectOnce

/**
 * Monitors for and notifies about the height updates of the provided [ref] via the [onHeightChanged] callback.
 * */
inline fun useRefHeightMonitor(ref: RefObject<Element>, crossinline onHeightChanged: (Int) -> Unit) = useEffectOnce {
    val resizeObserver = ref.current?.let { element ->
        var previousHeight = 0
        ResizeObserver { entries, _ ->
            val newHeight = entries.firstOrNull()?.target?.clientHeight
            if (newHeight != null && newHeight != previousHeight) {
                previousHeight = newHeight
                onHeightChanged(newHeight)
            }
        }.also { it.observe(element) }
    }
    cleanup { resizeObserver?.disconnect() }
}
