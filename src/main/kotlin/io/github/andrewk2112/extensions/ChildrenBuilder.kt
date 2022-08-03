package io.github.andrewk2112.extensions

import react.*

/**
 * Syntax sugar to declare an element with some class (classes) in one line.
 *
 * @param type       The type of element to be inserted.
 * @param classNames Class names to be set to the element.
 * @param block      Contents of the element.
 * */
inline fun <P> ChildrenBuilder.withClassName(
    type: ElementType<P>,
    vararg classNames: String,
    crossinline block: @ReactDsl P.() -> Unit
) where P : PropsWithClassName, P : ChildrenBuilder = type {
    setClassName(classNames.joinToString(separator = " "))
    block(this)
}
