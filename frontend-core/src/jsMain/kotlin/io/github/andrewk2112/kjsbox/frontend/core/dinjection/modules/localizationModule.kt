package io.github.andrewk2112.kjsbox.frontend.core.dinjection.modules

import io.github.andrewk2112.kjsbox.frontend.core.localization.LocalizationEngine
import org.kodein.di.DI
import org.kodein.di.bindSingleton

/** Provides a [LocalizationEngine]. */
internal val localizationModule = DI.Module("Localization") {
    bindSingleton { LocalizationEngine() }
}
