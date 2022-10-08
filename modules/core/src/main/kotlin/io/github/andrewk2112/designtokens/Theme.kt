package io.github.andrewk2112.designtokens

import io.github.andrewk2112.designtokens.system.ThemedFontSizes
import io.github.andrewk2112.designtokens.system.ThemedPalette
import io.github.andrewk2112.dinjection.di
import org.kodein.di.instance

/**
 * Provides access to themed style values (system design tokens).
 * */
object Theme {
    val fontSizes: ThemedFontSizes by di.instance()
    val palette:   ThemedPalette   by di.instance()
}