package io.github.andrewk2112.designtokens.reference

import kotlinx.css.LinearDimension
import kotlinx.css.properties.LineHeight
import kotlinx.css.rem

/**
 * All source font sizes - to be used in derived design tokens and styles.
 * */
class FontSizes {

    val relativep85:  LinearDimension get() = 0.85.rem
    val relativep875: LinearDimension get() = 0.875.rem
    val relativep9:   LinearDimension get() = 0.9.rem
    val relativep95:  LinearDimension get() = 0.95.rem
    val relative1:    LinearDimension get() = 1.rem
    val relative1p1:  LinearDimension get() = 1.1.rem
    val relative1p2:  LinearDimension get() = 1.2.rem
    val relative1p25: LinearDimension get() = 1.25.rem
    val relative1p5:  LinearDimension get() = 1.5.rem
    val relative2:    LinearDimension get() = 2.rem
    val relative3p65: LinearDimension get() = 3.65.rem

    val lh1p5: LineHeight get() = LineHeight("1.5")

}
