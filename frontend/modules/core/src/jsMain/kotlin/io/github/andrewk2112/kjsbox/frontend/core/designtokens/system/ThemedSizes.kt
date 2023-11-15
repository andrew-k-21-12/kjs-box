package io.github.andrewk2112.kjsbox.frontend.example.designtokens.system

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.core.designtokens.ContextedSizes
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.reference.ReferenceSizes

class SystemSizes(private val sizes: ReferenceSizes) : ContextedSizes<Context>() {
    val stroke1 = get { sizes.absolute1 }
}
