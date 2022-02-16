package io.github.andrewk2112.dinjection.modules

import io.github.andrewk2112.redux.StoreFactory
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

/** Provides the app's store. */
val storeModule = DI.Module("Store") {
    bindSingleton { StoreFactory(instance()) }
}
