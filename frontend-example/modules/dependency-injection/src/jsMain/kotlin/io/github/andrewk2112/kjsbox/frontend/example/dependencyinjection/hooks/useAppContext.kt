package io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.hooks

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.core.redux.reducers.ContextReducer

/**
 * A getter hook to read the app's [Context] simpler.
 */
fun useAppContext(): Context = useInjected<ContextReducer>().useSelector()
