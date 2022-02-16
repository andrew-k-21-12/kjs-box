package io.github.andrewk2112.designtokens

import io.github.andrewk2112.designtokens.reference.FontFaces
import io.github.andrewk2112.designtokens.reference.FontSizes
import io.github.andrewk2112.designtokens.reference.Palette
import io.github.andrewk2112.dinjection.di
import org.kodein.di.instance

/**
 * Provides access to source style values (reference design tokens).
 * */
object StyleValues {
    val fontFaces: FontFaces by di.instance()
    val fontSizes: FontSizes by di.instance()
    val palette:   Palette   by di.instance()
}
