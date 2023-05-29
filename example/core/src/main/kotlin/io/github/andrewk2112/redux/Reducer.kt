package io.github.andrewk2112.redux

import react.redux.useSelector as reduxUseSelector
import react.redux.useDispatch as reduxUseDispatch
import redux.RAction

/**
 * A template to create reducers.
 * A reducer can be treated as a state-only (and maybe more extended as well) view model done in the reactive way:
 * it accepts actions and returns new states accordingly.
 *
 * @param P Parent state.
 * @param S Reducer-related state.
 * @param A Action.
 */
abstract class Reducer<P, S, A : RAction> {

    // Public.

    /**
     * This is a key method of any reducer:
     * it accepts the current [parentState] to extract a reducer-related state from it,
     * processes it according to the input [action]
     * and returns the corresponding new state.
     */
    operator fun invoke(parentState: P, action: RAction): S = reduce(extractCurrentState(parentState), action)



    // To be overridden.

    /**
     * How to update the current [state] according to an incoming [action].
     *
     * @param state  The current state of the reducer.
     * @param action An incoming action to be processed.
     *
     * @return A new state according to the [action]'s processing.
     */
    protected abstract fun reduce(state: S, action: RAction): S

    /**
     * Describes how to extract a reducer-related state from the [parentState].
     */
    protected abstract fun extractCurrentState(parentState: P): S



    // Can be overridden.

    /**
     * Provides a hook (a kind of live value reader in this case) for the reducer-related state.
     * It's hidden (protected) by default, so it's up to a particular implementation to make it public.
     */
    protected open fun useSelector(): S = reduxUseSelector(::extractCurrentState)

    /**
     * Provides an update hook allowing to send actions to the reducer.
     * It's hidden (protected) by default, so it's up to a particular implementation to make it public.
     */
    protected open fun useDispatch(): (A) -> dynamic = reduxUseDispatch()

}
