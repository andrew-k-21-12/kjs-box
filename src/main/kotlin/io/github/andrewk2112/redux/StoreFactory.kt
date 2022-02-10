package io.github.andrewk2112.redux

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.redux.reducers.ContextReducer
import redux.*

/**
 * Defines how to create a [Store].
 * A [Store] can be treated like a facade on top of all global reducers.
 * It can be a good place to provide mock or other specific reducers according to the current environment.
 * */
class StoreFactory {

    // Public.

    /**
     * Creates a configured [Store] with an initial state and all global reducers.
     * */
    fun create(): Store<AppState, RAction, WrapperAction> = createStore(
        ::rootReducer,
        createInitialState(),
        rEnhancer()
    )



    // Private.

    /**
     * Describes how to map the current [state] according to an incoming [action]
     * and returns a new result state.
     * */
    private fun rootReducer(state: AppState, action: RAction) = AppState(
        ContextReducer.reduce(state, action)
    )

    /**
     * Creates the initial global state.
     * */
    private fun createInitialState() = AppState(
        Context(Context.ScreenSize.DESKTOP, Context.ColorMode.LIGHT)
    )

}
