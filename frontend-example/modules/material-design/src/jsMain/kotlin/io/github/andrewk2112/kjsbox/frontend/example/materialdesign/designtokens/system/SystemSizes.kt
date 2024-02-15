package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system

import io.github.andrewk2112.kjsbox.frontend.designtokens.ContextedSizes
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.reference.ReferenceSizes

class SystemSizes(private val sizes: ReferenceSizes) : ContextedSizes<Context>() {
    val stroke = get { sizes.absolute1 }
}
