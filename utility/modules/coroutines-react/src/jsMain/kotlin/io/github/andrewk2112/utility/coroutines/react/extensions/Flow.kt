package io.github.andrewk2112.utility.coroutines.react.extensions

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import react.StateInstance
import react.useEffectOnce
import react.useState
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext



// Public.

/**
 * Converts a receiver [Flow] into React's state.
 *
 * This function utilizes React hooks under the hood - so keep in mind all restrictions related to hooks.
 * The return type is nullable, because React's state requires some initial value all the time,
 * while the [Flow] can provide its first emission after a delay.
 *
 * The [Flow]'s collection happens only when the wrapping React component is in active (rendering) state,
 * it will be cancelled as soon as the component is about to be cleaned up (removed from the components hierarchy).
 * By default, the [Flow] gets collected on
 * [Dispatchers.Main.immediate][kotlinx.coroutines.MainCoroutineDispatcher.immediate],
 * but this behavior can be changed by providing a specific dispatcher in the [context].
 *
 * No error handling is provided,
 * add some [catch operator][kotlinx.coroutines.flow.catch] to the [Flow] to process exceptions as needed.
 * At the same time, no cancellation for the original context (its [Job]) will be invoked in the case of failure,
 * because this function creates its own [SupervisorJob] for execution
 * (which is a child for an optional [Job] of the [context]).
 */
fun <T : Any> Flow<T>.asReactState(context: CoroutineContext = EmptyCoroutineContext): StateInstance<T?> =
    useState<T>().also { collectWithContext(context, it.component2()::invoke) }

/**
 * Almost the same as similar [asReactState] but works with non-`null` values because of the provided [initialValue].
 */
fun <T : Any> Flow<T>.asReactState(
    initialValue: T,
    context: CoroutineContext = EmptyCoroutineContext
): StateInstance<T> =
    useState(initialValue).also { collectWithContext(context, it.component2()::invoke) }

/**
 * See the docs for the similar [asReactState] which accepts an initial value.
 * This initial value is taken from a [StateFlow.value].
 */
fun <T : Any> StateFlow<T>.asReactState(context: CoroutineContext = EmptyCoroutineContext): StateInstance<T> =
    asReactState(value, context)

/**
 * Almost identical to [asReactState] accepting a [CoroutineContext] with the following differences:
 * - doesn't create any [CoroutineScope], uses only the provided one without cancelling it on normal circumstances;
 * - if there is some failure during the [Flow]'s collection, the provided [scope] will be cancelled as well
 *   (if it's not a [SupervisorJob], of course).
 */
fun <T : Any> Flow<T>.asReactState(scope: CoroutineScope): StateInstance<T?> =
    useState<T>().also { collectInScope(scope, it.component2()::invoke) }

/**
 * Almost the same as similar [asReactState] but works with non-`null` values because of the provided [initialValue].
 */
fun <T : Any> Flow<T>.asReactState(initialValue: T, scope: CoroutineScope): StateInstance<T> =
    useState(initialValue).also { collectInScope(scope, it.component2()::invoke) }

/**
 * See the docs for the similar [asReactState] which accepts an initial value.
 * This initial value is taken from a [StateFlow.value].
 */
fun <T : Any> StateFlow<T>.asReactState(scope: CoroutineScope): StateInstance<T> = asReactState(value, scope)



// Private.

private fun <T : Any> Flow<T>.collectWithContext(context: CoroutineContext, collector: (T) -> Unit) {

    // Make sure the flow collection and related allocations are performed only once during the whole rendering phase.
    useEffectOnce {

        // The context is configured as follows:
        // - setting a default execution dispatcher;
        // - overriding this dispatcher if another one is provided in the `context`;
        // - creating a separate job for it to prevent the cancellation of one possibly set in the `context`:
        //   it's important to set the parent job for it to propagate cancellations
        //   (when the parent was cancelled or failed).
        val scope = CoroutineScope(Dispatchers.Main.immediate + context + SupervisorJob(context[Job]))

        scope.launch {
            collect(collector)
        }

        // Aborting the collection when the React component is about to be detached.
        cleanup { scope.cancel() }

    }

}

private fun <T : Any> Flow<T>.collectInScope(scope: CoroutineScope, collector: (T) -> Unit) {
    useEffectOnce {
        val job = scope.launch {
            collect(collector)
        }
        cleanup { job.cancel() }
    }
}
