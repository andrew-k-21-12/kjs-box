package io.github.andrewk2112.kjsbox.frontend.dinjection.modules

import io.github.andrewk2112.kjsbox.frontend.hooks.WindowWidthMonitor
import org.kodein.di.DI
import org.kodein.di.bindSingleton

/** Provides all hooks. */
internal val hooksModule = DI.Module("Hooks") {
    bindSingleton { WindowWidthMonitor() }
}
