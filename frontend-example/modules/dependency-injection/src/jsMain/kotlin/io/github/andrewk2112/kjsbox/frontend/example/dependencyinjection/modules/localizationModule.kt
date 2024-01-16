package io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.modules

import io.github.andrewk2112.kjsbox.frontend.core.localization.LocalizationEngine
import org.kodein.di.DI
import org.kodein.di.bindSingleton

/** Provides a [LocalizationEngine]. */
internal val localizationModule inline get() = DI.Module("Localization") {
    bindSingleton { LocalizationEngine() }
}
