package io.github.andrewk2112.kjsbox.frontend.core.extensions

import react.dom.events.SyntheticEvent
import web.dom.Element

/** Event's target with respecting its type. */
val <T : Element> SyntheticEvent<T, *>.typedTarget: T
    get() = target.unsafeCast<T>()
