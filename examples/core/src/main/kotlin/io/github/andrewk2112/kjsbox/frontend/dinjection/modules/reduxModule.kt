package io.github.andrewk2112.kjsbox.frontend.dinjection.modules

import io.github.andrewk2112.kjsbox.frontend.redux.StoreFactory
import io.github.andrewk2112.kjsbox.frontend.redux.reducers.ContextReducer
import org.kodein.di.*

/** Provides all reducers and their top wrapping store. */
internal val reduxModule = DI.Module("Redux") {
    bindSingleton { ContextReducer(instance()) }
    bindSingleton { StoreFactory(instance(), instance()) }
}
