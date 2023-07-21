package io.github.andrewk2112.kjsbox.frontend.designtokens

import io.github.andrewk2112.kjsbox.frontend.dinjection.di
import io.github.andrewk2112.kjsbox.frontend.designtokens.reference.*
import org.kodein.di.instance

/**
 * Provides access to source style values (reference design tokens).
 */
object StyleValues {
    val fontSizes: FontSizes by di.instance()
    val opacities: Opacities by di.instance()
    val palette:   Palette   by di.instance()
    val radii:     Radii     by di.instance()
    val sizes:     Sizes     by di.instance()
    val spacing:   Spacing   by di.instance()
    val time:      Time      by di.instance()
    val timing:    Timing    by di.instance()
}