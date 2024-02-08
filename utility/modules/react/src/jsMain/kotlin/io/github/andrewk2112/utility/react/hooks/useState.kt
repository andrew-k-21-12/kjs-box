package io.github.andrewk2112.utility.react.hooks

import react.StateInstance
import react.useEffectOnce

/**
 * The same as [react.useState] but provides a [cleaner]
 * which is invoked with the latest instance of the state,
 * when a holding React component is about to be detached.
 */
fun <T> useState(initializer: () -> T, cleaner: (T) -> Unit): StateInstance<T> {
    val state = react.useState(initializer)
    useEffectOnce {
        cleanup { cleaner(state.component1()) }
    }
    return state
}
