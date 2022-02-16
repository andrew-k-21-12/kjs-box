package io.github.andrewk2112.dinjection.modules

import io.github.andrewk2112.designtokens.reference.FontFaces
import io.github.andrewk2112.designtokens.reference.FontSizes
import io.github.andrewk2112.designtokens.reference.Palette
import io.github.andrewk2112.designtokens.system.ThemedFontFaces
import io.github.andrewk2112.designtokens.system.ThemedFontSizes
import io.github.andrewk2112.designtokens.system.ThemedPalette
import org.kodein.di.DI
import org.kodein.di.bindSingleton

/** Provides all design tokens. */
val designTokensModule = DI.Module("DesignTokens") { // not sure if this module is really needed
    bindSingleton { FontFaces() }
    bindSingleton { FontSizes() }
    bindSingleton { Palette() }
    bindSingleton { ThemedFontFaces() }
    bindSingleton { ThemedFontSizes() }
    bindSingleton { ThemedPalette() }
}
