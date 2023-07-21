package io.github.andrewk2112.kjsbox.frontend.dinjection.modules

import io.github.andrewk2112.kjsbox.frontend.designtokens.system.ThemedFontSizes
import io.github.andrewk2112.kjsbox.frontend.designtokens.system.ThemedPalette
import io.github.andrewk2112.kjsbox.frontend.designtokens.system.ThemedSizes
import io.github.andrewk2112.kjsbox.frontend.designtokens.reference.*
import org.kodein.di.DI
import org.kodein.di.bindSingleton

/** Provides all design tokens. */
internal val designTokensModule = DI.Module("DesignTokens") { // not sure if this module is really needed
    bindSingleton { FontSizes() }
    bindSingleton { Opacities() }
    bindSingleton { Palette() }
    bindSingleton { Radii() }
    bindSingleton { Sizes() }
    bindSingleton { Spacing() }
    bindSingleton { Time() }
    bindSingleton { Timing() }
    bindSingleton { ThemedFontSizes() }
    bindSingleton { ThemedPalette() }
    bindSingleton { ThemedSizes() }
}