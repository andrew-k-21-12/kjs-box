package io.github.andrewk2112.extensions

import kotlinx.css.CssBuilder
import org.w3c.dom.Element

/**
 * A bridge to get the [offsetLeft](https://developer.mozilla.org/docs/Web/API/HTMLElement/offsetLeft) value from JS.
 * */
val Element.offsetLeft: Int get() = asDynamic().offsetLeft.unsafeCast<Int>()

/** A bridge to get the [offsetTop](https://developer.mozilla.org/docs/Web/API/HTMLElement/offsetTop) value from JS. */
val Element.offsetTop: Int get() = asDynamic().offsetTop.unsafeCast<Int>()

/** A style attribute to be manipulated using raw strings. */
var Element.style: String
    get() = asDynamic().style.unsafeCast<String>()
    set(value) {
        asDynamic().style = value
    }

/**
 * Constructs and sets a style using a higher level [CssBuilder].
 * */
fun Element.setStyle(builder: CssBuilder.() -> Unit) {
    style = CssBuilder(allowClasses = false).apply(builder).toString()
}
