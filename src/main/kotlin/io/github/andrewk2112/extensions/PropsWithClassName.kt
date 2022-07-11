package io.github.andrewk2112.extensions

import csstype.ClassName
import react.PropsWithClassName

/**
 * Syntax sugar to set a [className] without wrapping a [String] into the [ClassName].
 * */
fun PropsWithClassName.setClassName(className: String) {
    this.className = ClassName(className)
}
