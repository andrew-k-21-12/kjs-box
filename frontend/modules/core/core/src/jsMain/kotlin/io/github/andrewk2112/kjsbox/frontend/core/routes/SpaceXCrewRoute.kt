package io.github.andrewk2112.kjsbox.frontend.core.routes

import io.github.andrewk2112.kjsbox.frontend.core.routes.mutators.PathRouteMutator

/**
 * Points to the page allowing to search through the SpaceX crew.
 */
object SpaceXCrewRoute : PathRouteMutator {
    override val path: String = "/spacex-crew"
}
