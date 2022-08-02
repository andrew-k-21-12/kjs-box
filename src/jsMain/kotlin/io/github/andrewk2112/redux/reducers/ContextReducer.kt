package io.github.andrewk2112.redux.reducers

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.hooks.WindowWidthMonitor
import io.github.andrewk2112.redux.AppState
import io.github.andrewk2112.redux.Reducer
import redux.RAction

/**
 * Means of managing the global [Context] of the app.
 * */
class ContextReducer(
    private val windowWidthMonitor: WindowWidthMonitor
) : Reducer<AppState, Context, ContextReducer.Action>() {

    // Actions.

    sealed class Action : RAction {
        class UpdateScreenSize(val newValue: Context.ScreenSize) : Action()
        class UpdateColorMode(val newValue: Context.ColorMode) : Action()
    }



    // Public.

    /**
     * Turns on the hook to update the current [Context] according to screen size changes.
     * */
    fun useScreenSizeMonitor() {
        val dispatch = useDispatch()
        windowWidthMonitor.useWindowWidthMonitor {
            val newValue = Context.ScreenSize.fromRawWidth(it)
            dispatch(Action.UpdateScreenSize(newValue)); Unit
        }
    }



    // Implementations.

    override fun reduce(state: Context, action: RAction): Context {
        if (action !is Action) return state
        val newState = when (action) {
            is Action.UpdateScreenSize -> state.copy(screenSize = action.newValue)
            is Action.UpdateColorMode  -> state.copy(colorMode  = action.newValue)
        }
        return if (newState != state) newState else state
    }

    override fun extractCurrentState(parentState: AppState): Context = parentState.context

    public override fun useSelector(): Context = super.useSelector()

}
