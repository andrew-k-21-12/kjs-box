package io.github.andrewk2112.extensions

import org.w3c.dom.events.EventTarget

/** A bridge to get the [scrollTop](https://developer.mozilla.org/docs/Web/API/Element/scrollTop) value from JS. */
val EventTarget.scrollTop: Int get() = asDynamic().scrollTop.unsafeCast<Int>()
