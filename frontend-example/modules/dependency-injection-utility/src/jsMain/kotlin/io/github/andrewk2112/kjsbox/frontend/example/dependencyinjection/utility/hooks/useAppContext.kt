package io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.useRootComponent

/**
 * A getter hook to read the app's [Context] simpler.
 */
fun useAppContext(): Context = useRootComponent().getContextReducer().useSelector()
