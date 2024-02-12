package io.github.andrewk2112.utility.react.dom.extensions

import react.dom.events.EventHandler

/**
 * Converts a simple callback lambda into any type of required [EventHandler].
 */
fun <E> (() -> Unit).asEventHandler(): EventHandler<E> = unsafeCast<EventHandler<E>>()
