package io.github.andrewk2112.redux.reducers

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.redux.AppState
import io.github.andrewk2112.redux.Reducer
import redux.RAction

/**
 * Means of managing the global [Context] of the app.
 * */
object ContextReducer : Reducer<AppState, Context, ContextReducer.Action>() {

    // Actions.

    sealed class Action : RAction {
        class UpdateScreenSize(val newValue: Context.ScreenSize) : Action()
        class UpdateColorMode(val newValue: Context.ColorMode) : Action()
    }



    // Implementations.

    override fun reduce(state: Context, action: RAction): Context {
        if (action !is Action) return state
        return when (action) {
            is Action.UpdateScreenSize -> state.copy(screenSize = action.newValue)
            is Action.UpdateColorMode  -> state.copy(colorMode  = action.newValue)
        }
    }

    override fun extractCurrentState(parentState: AppState): Context = parentState.context

}
