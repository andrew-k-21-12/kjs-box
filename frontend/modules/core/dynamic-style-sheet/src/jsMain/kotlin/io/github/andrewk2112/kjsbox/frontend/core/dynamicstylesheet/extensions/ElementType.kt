package io.github.andrewk2112.kjsbox.frontend.core.dynamicstylesheet.extensions

import react.*
import web.cssom.ClassName



// Public.

/**
 * Provides a short way for assigning CSS class names to elements when initializing them.
 *
 * As it's used very frequently, it was decided to avoid any function names at all.
 * The drawback of the requirement to prepend "+" is going to be resolved
 * when Kotlin starts to support `context(...)` in production.
 */
inline operator fun <P> ElementType<P>.invoke(
    vararg classes: String,
    crossinline block: @ReactDsl P.() -> Unit,
): ReactElement<P> where P : PropsWithClassName,
                         P : ChildrenBuilder = create {
    className = classes.toClassName()
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
    vararg classes: String
): ReactElement<P> where P : PropsWithClassName,
                         P : ChildrenBuilder = create { className = classes.toClassName() }

/**
 * The same as other [invoke]s - for cases when only one [clazz] is needed to be applied
 * and the initialization block is not needed.
 */
inline operator fun <P> ElementType<P>.invoke(
    clazz: String
): ReactElement<P> where P : PropsWithClassName,
                         P : ChildrenBuilder = create { className = ClassName(clazz) }



// Internal.

/**
 * Simplifies string array conversions to [ClassName] for internal needs.
 */
@PublishedApi
internal fun Array<out String>.toClassName() = ClassName(joinToString(separator = " "))
