package io.github.andrewk2112.kjsbox.frontend.routes.mutators

/**
 * Helps to describe routes nested inside each other.
 */
interface NestedRouteMutator {

    /** A path parent to the current. */
    val parent: RouteMutator

}
