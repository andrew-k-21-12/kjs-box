package io.github.andrewk2112.hooks

import react.EffectBuilder
import react.useEffect
import react.useRef

/**
 * Keeps (returns) the previous value of the provided [newValue],
 * the previous value is always updated on each rendering iteration.
 */
fun <T : Any> useIndependentPrevious(newValue: T): T = usePreviousBuilder(newValue) { useEffect(it) }

/**
 * Keeps (returns) the previous value of the provided [newValue],
 * the previous value is updated only when some of its [dependencies] change:
 * by default the previous value is updated only when the [newValue] is changed.
 */
fun <T : Any> usePrevious(newValue: T, vararg dependencies: Any? = arrayOf(newValue)): T =
    usePreviousBuilder(newValue) {
        useEffect(dependencies = dependencies, it)
    }

private inline fun <T : Any> usePreviousBuilder(newValue: T, useEffectCaller: (EffectBuilder.() -> Unit) -> Unit): T {

    // There are no cases when the previous value is not defined, initially it is equal to the current value.
    val previousValueHolder = useRef(newValue)

    // Saves the current value as the previous only on the rendering completion.
    useEffectCaller { previousValueHolder.current = newValue }

    // This cast is guaranteed to happen successfully as the holder is always initialized with a non-null value.
    return previousValueHolder.current as T

}
