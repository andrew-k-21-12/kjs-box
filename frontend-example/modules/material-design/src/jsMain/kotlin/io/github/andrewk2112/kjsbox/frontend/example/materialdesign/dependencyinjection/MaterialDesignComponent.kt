package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection

import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.RootComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.modules.designTokensModule
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.modules.rootComponentMappingModuleFactory
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import io.github.andrewk2112.utility.kodein.KodeinDirectInjection

/**
 * Material design dependency injection component containing related dependencies.
 *
 * This component and its related modules can be substituted by different Gradle modules
 * (for test builds or other variants) - just keep the name and package the same
 * (or better extract an interface to substitute Gradle modules only for component's entry point, holder, module).
 */
class MaterialDesignComponent(rootComponent: RootComponent) {

    fun getMaterialDesignTokens(): MaterialDesignTokens = kodeinInjection()

    private val kodeinInjection = KodeinDirectInjection(
        designTokensModule,
        rootComponentMappingModuleFactory(rootComponent)
    )

}
