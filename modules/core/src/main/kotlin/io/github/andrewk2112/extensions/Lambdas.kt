package io.github.andrewk2112.extensions

import react.dom.events.MouseEventHandler

/**
 * Converts a simple callback lambda into the [MouseEventHandler].
 */
fun (() -> Unit).asMouseEventHandler(): MouseEventHandler<*> = unsafeCast<MouseEventHandler<*>>()
