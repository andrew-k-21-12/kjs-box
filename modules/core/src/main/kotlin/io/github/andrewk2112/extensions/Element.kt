package io.github.andrewk2112.extensions

import kotlinx.css.CssBuilder
import org.w3c.dom.Element

/** A style attribute to be manipulated using raw strings. */
var Element.style: String
    get() = asDynamic().style.unsafeCast<String>()
    set(value) {
        asDynamic().style = value
    }

/**
 * Constructs and sets a style using a higher level [CssBuilder].
 */
fun Element.setStyle(builder: CssBuilder.() -> Unit) {
    style = CssBuilder(allowClasses = false).apply(builder).toString()
}
