package io.github.andrewk2112.kjsbox.frontend.designtokens

import io.github.andrewk2112.kjsbox.frontend.designtokens.system.ThemedFontSizes
import io.github.andrewk2112.kjsbox.frontend.designtokens.system.ThemedPalette
import io.github.andrewk2112.kjsbox.frontend.designtokens.system.ThemedSizes
import io.github.andrewk2112.kjsbox.frontend.dinjection.di
import org.kodein.di.instance

/**
 * Provides access to themed style values (system design tokens).
 */
object Theme {
    val fontSizes: ThemedFontSizes by di.instance()
    val palette:   ThemedPalette   by di.instance()
    val sizes:     ThemedSizes     by di.instance()
}
