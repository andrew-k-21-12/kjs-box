package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection

import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.modules.designTokensModule
import org.kodein.di.DI

/**
 * Material design dependency injection component containing related dependencies.
 */
val materialDesignComponent: DI by lazy {
    DI {
        import(designTokensModule)
    }
}
