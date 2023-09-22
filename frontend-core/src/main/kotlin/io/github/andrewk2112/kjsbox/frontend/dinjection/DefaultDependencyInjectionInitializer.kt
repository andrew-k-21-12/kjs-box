package io.github.andrewk2112.kjsbox.frontend.core.dinjection

import io.github.andrewk2112.kjsbox.frontend.core.dinjection.modules.designTokensModule
import io.github.andrewk2112.kjsbox.frontend.core.dinjection.modules.hooksModule
import io.github.andrewk2112.kjsbox.frontend.core.dinjection.modules.localizationModule
import io.github.andrewk2112.kjsbox.frontend.core.dinjection.modules.reduxModule
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
