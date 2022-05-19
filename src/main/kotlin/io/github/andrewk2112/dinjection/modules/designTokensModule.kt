package io.github.andrewk2112.dinjection.modules

import io.github.andrewk2112.designtokens.reference.*
import io.github.andrewk2112.designtokens.system.ThemedFontSizes
import io.github.andrewk2112.designtokens.system.ThemedPalette
import org.kodein.di.DI
import org.kodein.di.bindSingleton

/** Provides all design tokens. */
val designTokensModule = DI.Module("DesignTokens") { // not sure if this module is really needed
    bindSingleton { FontFaces() }
    bindSingleton { FontSizes() }
    bindSingleton { Palette() }
    bindSingleton { Radii() }
    bindSingleton { Sizes() }
    bindSingleton { Spacing() }
    bindSingleton { ThemedFontSizes() }
    bindSingleton { ThemedPalette() }
}
