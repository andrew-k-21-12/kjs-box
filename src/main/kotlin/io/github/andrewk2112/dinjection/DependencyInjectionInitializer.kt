package io.github.andrewk2112.dinjection

import org.kodein.di.DI

/**
 * A protocol to perform initialization of the dependency injection.
 * */
interface DependencyInjectionInitializer {

    /**
     * How to initialize the dependency injection.
     *
     * @param builder A builder to configure dependencies and other initializations.
     * */
    fun initialize(builder: DI.MainBuilder)

}
