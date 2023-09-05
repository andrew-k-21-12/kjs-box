package io.github.andrewk2112.kjsbox.frontend.designtokens.reference

import kotlinx.css.properties.cubicBezier
import kotlinx.css.properties.Timing as CssTiming

/**
 * A kind of palette for transition timing values.
 */
class Timing {
    val cubicBezier1: CssTiming get() = cubicBezier(0.4, 0.0, 0.2, 1.0)
    val ease:         CssTiming get() = CssTiming.ease
    val linear:       CssTiming get() = CssTiming.linear
}
