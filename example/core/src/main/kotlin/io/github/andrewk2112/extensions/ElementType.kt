package io.github.andrewk2112.extensions

import react.*
import web.cssom.ClassName

/**
 * Provides a short way for assigning CSS class names to elements when initializing them.
 *
 * As it's used very frequently, it was decided to avoid any function names at all.
 * The drawback of the requirement to prepend "+" is going to be resolved
 * when Kotlin starts to support `context(...)` in production.
 */
inline operator fun <P> ElementType<P>.invoke(
    vararg clazz: String,
    crossinline block: @ReactDsl P.() -> Unit,
): ReactElement<P> where P : PropsWithClassName,
                         P : ChildrenBuilder = create {
    className = clazz.toClassName()
    block.invoke(this)
}

/**
 * The same as other [invoke]s - for cases when only one [clazz] is needed to be applied.
 */
inline operator fun <P> ElementType<P>.invoke(
    clazz: String,
    crossinline block: @ReactDsl P.() -> Unit,
): ReactElement<P> where P : PropsWithClassName,
                         P : ChildrenBuilder = create {
    className = ClassName(clazz)
    block.invoke(this)
}

/**
 * The same as other [invoke]s - for cases when the initialization block is not needed.
 */
inline operator fun <P> ElementType<P>.invoke(
    vararg clazz: String
): ReactElement<P> where P : PropsWithClassName,
                         P : ChildrenBuilder = create { className = clazz.toClassName() }

/**
 * The same as other [invoke]s - for cases when only one [clazz] is needed to be applied
 * and the initialization block is not needed.
 */
inline operator fun <P> ElementType<P>.invoke(
    clazz: String
): ReactElement<P> where P : PropsWithClassName,
                         P : ChildrenBuilder = create { className = ClassName(clazz) }

