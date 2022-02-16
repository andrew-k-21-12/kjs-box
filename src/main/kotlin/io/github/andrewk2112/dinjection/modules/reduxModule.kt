package io.github.andrewk2112.dinjection.modules

import io.github.andrewk2112.redux.reducers.ContextReducer
import org.kodein.di.*

/** Provides all reducers. */
val reduxModule = DI.Module("Redux") {
    bindSingleton { ContextReducer(instance()) }
}
