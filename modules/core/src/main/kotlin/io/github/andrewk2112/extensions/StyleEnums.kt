package io.github.andrewk2112.extensions

import kotlinx.css.CssValue

/**
 * A wrapper to provide [outline-style](https://developer.mozilla.org/docs/Web/CSS/outline-style) values.
 * */
class OutlineStyle constructor(value: String) : CssValue(value) {

    companion object {

        val inherit     = OutlineStyle("inherit")
        val initial     = OutlineStyle("initial")
        val revert      = OutlineStyle("revert")
        val revertLayer = OutlineStyle("revert-layer")
        val unset       = OutlineStyle("unset")

        val auto   = OutlineStyle("auto")
        val none   = OutlineStyle("none")
        val dotted = OutlineStyle("dotted")
        val dashed = OutlineStyle("dashed")
        val solid  = OutlineStyle("solid")
        val double = OutlineStyle("double")
        val groove = OutlineStyle("groove")
        val ridge  = OutlineStyle("ridge")
        val inset  = OutlineStyle("inset")
        val outset = OutlineStyle("outset")

    }

}
