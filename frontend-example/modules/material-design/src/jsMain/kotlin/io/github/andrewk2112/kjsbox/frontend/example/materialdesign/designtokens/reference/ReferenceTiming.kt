package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.reference

import io.github.andrewk2112.kjsbox.frontend.designtokens.Timing
import kotlinx.css.properties.cubicBezier
import kotlinx.css.properties.Timing as CssTiming

class ReferenceTiming : Timing {
    val cubicBezier1 = cubicBezier(0.4, 0.0, 0.2, 1.0)
    val ease         = CssTiming.ease
    val linear       = CssTiming.linear
}
