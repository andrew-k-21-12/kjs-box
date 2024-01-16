package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.modules

import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.RootComponent
import org.kodein.di.DI
import org.kodein.di.bindProvider

/**
 * Creates a [DI.Module] allowing to use required dependencies from the [RootComponent].
 */
fun rootComponentMappingModuleFactory(rootComponent: RootComponent) = DI.Module("RootComponentMapping") {
    bindProvider { rootComponent.getDesignTokens() } // called each when the dependency is requested
}
