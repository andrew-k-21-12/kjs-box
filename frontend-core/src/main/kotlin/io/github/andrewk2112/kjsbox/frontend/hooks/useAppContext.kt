package io.github.andrewk2112.kjsbox.frontend.hooks

import io.github.andrewk2112.kjsbox.frontend.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.redux.reducers.ContextReducer

/**
 * A getter hook to read the app's [Context] simpler.
 */
fun useAppContext(): Context = useInjected<ContextReducer>().useSelector()
