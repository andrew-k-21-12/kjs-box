package io.github.andrewk2112.routes.mutators

/**
 * Describes a [path] for the route.
 * */
interface PathRouteMutator : RouteMutator {

    /** A path inherent to the current route. */
    val path: String

}
