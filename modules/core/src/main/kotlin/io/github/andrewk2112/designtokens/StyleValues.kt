package io.github.andrewk2112.designtokens

import io.github.andrewk2112.designtokens.reference.*
import io.github.andrewk2112.dinjection.di
import org.kodein.di.instance

/**
 * Provides access to source style values (reference design tokens).
 * */
object StyleValues {
    val fontSizes: FontSizes by di.instance()
    val palette:   Palette   by di.instance()
    val radii:     Radii     by di.instance()
    val sizes:     Sizes     by di.instance()
    val spacing:   Spacing   by di.instance()
    val time:      Time      by di.instance()
    val timing:    Timing    by di.instance()
}
