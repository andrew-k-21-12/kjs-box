package io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection

import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.modules.designTokensModule
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.modules.hooksModule
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.modules.localizationModule
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.modules.reduxModule
import org.kodein.di.DI

/**
 * Includes default modules to provide injectable dependencies.
 */
internal class DefaultDependencyInjectionInitializer : DependencyInjectionInitializer {

    override fun initialize(builder: DI.MainBuilder) = with(builder) {
        import(hooksModule)
        import(reduxModule)
        import(designTokensModule)
        import(localizationModule)
    }

}
