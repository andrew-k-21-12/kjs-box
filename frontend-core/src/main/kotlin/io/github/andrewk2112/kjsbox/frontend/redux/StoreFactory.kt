package io.github.andrewk2112.kjsbox.frontend.redux

import io.github.andrewk2112.kjsbox.frontend.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.designtokens.Context.ColorMode
import io.github.andrewk2112.kjsbox.frontend.designtokens.Context.ScreenSize
import io.github.andrewk2112.kjsbox.frontend.hooks.WindowWidthMonitor
import io.github.andrewk2112.kjsbox.frontend.redux.reducers.ContextReducer
import redux.*

/**
 * Defines how to create a [Store].
 * A [Store] can be treated like a facade on top of all global reducers enabling them to work.
 */
class StoreFactory(private val windowWidthMonitor: WindowWidthMonitor, private val contextReducer: ContextReducer) {

    // Public.

    /**
     * Creates a configured [Store] with an initial state and all global reducers.
     */
    fun create(): Store<AppState, RAction, WrapperAction> = createStore(
        ::rootReducer,
        createInitialState(),
        rEnhancer()
    )



    // Private.

    /**
     * Describes how to map the current [state] according to an incoming [action]
     * and returns a new result state.
     */
    private fun rootReducer(state: AppState, action: RAction) = AppState(
        // It's correct to use only one store for most apps:
        // having multiple reducers that are further split into a reducer tree
        // is the way to keep updates modular in Redux.
        contextReducer(state, action)
    )

    /**
     * Creates the initial global state.
     */
    private fun createInitialState() = AppState(
        Context(ScreenSize.fromRawWidth(windowWidthMonitor.windowWidth), ColorMode.LIGHT)
    )

}
