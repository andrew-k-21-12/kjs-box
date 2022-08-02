package io.github.andrewk2112.hooks

import org.w3c.dom.Element
import org.w3c.dom.events.Event
import org.w3c.dom.events.WheelEvent
import react.RefObject
import react.useEffect

/**
 * Monitors the scrolling of the provided [ref].
 *
 * @param ref          A [RefObject] to monitor the scrolling of.
 * @param dependencies Registers the underlying listener again on changes of these dependencies.
 * @param onScrolled   Notifies about the scrolling events observed.
 * */
inline fun useRefScrollMonitor(
    ref: RefObject<Element>,
    vararg dependencies: Any?,
    crossinline onScrolled: (scrollTop: Double, deltaY: Double) -> Unit
) = useEffect(dependencies) {

    // Making sure something is assigned to the ref.
    val element = ref.current ?: return@useEffect

    // Binding the callback.
    val wheelListener = { event: Event ->
        onScrolled.invoke(element.scrollTop, event.unsafeCast<WheelEvent>().deltaY)
    }

    // Setting up and removing the listener.
    element.addEventListener("wheel", wheelListener)
    cleanup {
        element.removeEventListener("wheel", wheelListener)
    }

}
