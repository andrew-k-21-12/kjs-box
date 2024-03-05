package io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.kodein.modules

import io.github.andrewk2112.kjsbox.frontend.example.localization.LocalizationEngine
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

/** Provides a [LocalizationEngine]. */
internal val localizationModule inline get() = DI.Module("Localization") {
    bindSingleton { LocalizationEngine(instance()) }
}
