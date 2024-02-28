package io.github.andrewk2112.kjsbox.frontend.route

/**
 * Describes any kinds of routes hierarchies.
 *
 * Implementations of this interface can be used to declare both route schemes and their actual values. For example:
 *
 * ```kotlin
 * class CategoryRoute(override val path: String) : Route {
 *     override val parent = null
 * }
 * class ItemRoute(override val parent: CategoryRoute, override val path: String) : Route
 * ```
 *
 * can serve to declare route schemes:
 *
 * ```kotlin
 * val categoryRoute = CategoryRoute(":category")
 * val itemRoute     = ItemRoute(categoryRoute, ":item")
 * ```
 *
 * and inflate their corresponding actual routes:
 *
 * ```kotlin
 * val destinationPath = ItemRoute(CategoryRoute("cakes"), "napoleon").absolutePath()
 * ```
 *
 * See also [absolutePath].
 */
interface Route {

    /** References a parent [Route], should be `null` if there are no parent [Route]s. */
    val parent: Route?

    /** Path of the route relative to its [parent]. */
    val path: String

}

/**
 * Gathers [Route]'s absolute path: joins all [Route.path]s until the root one (having no [Route.parent]) is reached.
 *
 * Has no caching.
 */
fun Route.absolutePath(): String = parent?.let { "${it.absolutePath()}/$path" } ?: "/$path"
