package io.github.andrewk2112.kjsbox.frontend.example.designtokens.system

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.ContextedPalette
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.reference.ReferencePalette

class SystemPalette(private val palette: ReferencePalette) : ContextedPalette<Context>() {
    val action       = get { palette.blue1 }
    val actionDimmed = get { palette.blue2 }
}
