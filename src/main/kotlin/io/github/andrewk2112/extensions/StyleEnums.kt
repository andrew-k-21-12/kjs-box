package io.github.andrewk2112.extensions

import kotlinx.css.CssValue

/**
 * A wrapper to provide [aspect-ratio](https://developer.mozilla.org/docs/Web/CSS/aspect-ratio) values.
 * */
class AspectRatio constructor(value: String) : CssValue(value) {

    /**
     * A short form to provide the ratio, height defaults to 1.
     * */
    constructor(widthRatio: Number) : this(widthRatio.toString())

    constructor(widthRatio: Number, heightRatio: Number) : this("$widthRatio / $heightRatio")

    companion object {
        val inherit     = AspectRatio("inherit")
        val initial     = AspectRatio("initial")
        val revert      = AspectRatio("revert")
        val revertLayer = AspectRatio("revert-layer")
        val unset       = AspectRatio("unset")
        val auto        = AspectRatio("auto")
    }

}
