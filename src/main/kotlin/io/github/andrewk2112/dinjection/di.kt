package io.github.andrewk2112.dinjection

import org.kodein.di.*

/** The app's main dependency injection component. */
val di by lazy {
    DI(init = diInitializer::initialize)
}

/**
 * A dependency injection initializer - can be substituted with other instances before any access to the [di]
 * to provide other (e.g. mock) injection modules and configs.
 * */
var diInitializer: DependencyInjectionInitializer = DefaultDependencyInjectionInitializer()
