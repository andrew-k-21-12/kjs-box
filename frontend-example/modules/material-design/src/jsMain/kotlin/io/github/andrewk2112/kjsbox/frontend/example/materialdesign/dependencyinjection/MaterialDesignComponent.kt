package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection

import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.RootComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.modules.designTokensModule
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.modules.rootComponentMappingModuleFactory
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import io.github.andrewk2112.utility.kodein.KodeinDirectInjection

/**
 * Material design dependency injection component providing related dependencies.
 *
 * This component and its related code can be reorganized in a way similar to the [RootComponent]
 * to substitute component's different implementations much easier.
 * Even more, there won't be any need in code updates
 * if the name and package of this component's implementations are kept the same:
 * in this case it's possible just to swap Gradle dependencies to provide different implementations
 * (for example, for test environments or additional variants).
 */
class MaterialDesignComponent(rootComponent: RootComponent) {

    fun getMaterialDesignTokens(): MaterialDesignTokens = kodeinInjection()

    private val kodeinInjection = KodeinDirectInjection(
        designTokensModule,
        rootComponentMappingModuleFactory(rootComponent)
    )

}
