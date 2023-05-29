package io.github.andrewk2112.extensions

import react.*

/**
 * Provides a super short way for assigning CSS [classNames] to elements when initializing them.
 *
 * As it's used very frequently, it was decided to avoid any function names at all.
 * The drawback of the requirement to prepend "+" is going to be resolved
 * when Kotlin starts to support `context(...)` in production.
 */
inline operator fun <P> ElementType<P>.invoke(
    vararg classNames: String,
    crossinline block: @ReactDsl P.() -> Unit,
): ReactElement<P> where P : PropsWithClassName,
                         P : ChildrenBuilder = create {
    className = classNames.toClassName()
    block.invoke(this)
}

/**
 * The same as the other [invoke] - for cases when the initialization block is not needed.
 */
inline operator fun <P> ElementType<P>.invoke(
    vararg classNames: String
): ReactElement<P> where P : PropsWithClassName,
                         P : ChildrenBuilder = create { className = classNames.toClassName() }
