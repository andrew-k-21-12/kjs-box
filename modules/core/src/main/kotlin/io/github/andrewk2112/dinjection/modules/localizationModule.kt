package io.github.andrewk2112.dinjection.modules

import io.github.andrewk2112.localization.LocalizationEngine
import org.kodein.di.DI
import org.kodein.di.bindSingleton

/** Provides a [LocalizationEngine]. */
internal val localizationModule = DI.Module("Localization") {
    bindSingleton { LocalizationEngine() }
}
