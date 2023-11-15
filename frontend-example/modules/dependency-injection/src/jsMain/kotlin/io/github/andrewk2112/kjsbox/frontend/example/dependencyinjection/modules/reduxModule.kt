package io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.modules

import io.github.andrewk2112.kjsbox.frontend.core.redux.StoreFactory
import io.github.andrewk2112.kjsbox.frontend.core.redux.reducers.ContextReducer
import org.kodein.di.*

/** Provides all reducers and their top wrapping store. */
internal val reduxModule = DI.Module("Redux") {
    bindSingleton { ContextReducer(instance()) }
    bindSingleton { StoreFactory(instance(), instance()) }
}
