package io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection

import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.modules.designTokensModule
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.modules.hooksModule
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.modules.localizationModule
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.modules.reduxModule
import org.kodein.di.*

/**
 * The app's main dependency injection component containing dependencies used everywhere - even in lazy modules.
 *
 * This component and its related modules can be substituted by different Gradle modules
 * (for test builds or other variants) - just keep the name and package the same.
 */
val rootComponent: DI by lazy {
    DI {
        import(designTokensModule)
        import(hooksModule)
        import(localizationModule)
        import(reduxModule)
    }
}
