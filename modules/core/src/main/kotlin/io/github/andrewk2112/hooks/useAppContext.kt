package io.github.andrewk2112.hooks

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.redux.reducers.ContextReducer

/**
 * A getter hook to read the app's [Context] simpler.
 */
fun useAppContext(): Context = useInjected<ContextReducer>().useSelector()
