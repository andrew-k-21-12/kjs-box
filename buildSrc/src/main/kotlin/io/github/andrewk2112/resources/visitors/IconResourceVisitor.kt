package io.github.andrewk2112.resources.visitors

import io.github.andrewk2112.models.IconResource
import io.github.andrewk2112.resources.ResourcePaths
import io.github.andrewk2112.resources.ResourceVisitingException
import io.github.andrewk2112.utility.CollectingVisitor
import io.github.andrewk2112.utility.Result

/**
 * A [CollectingVisitor] to gather all [IconResource] metadata.
 */
internal class IconResourceVisitor : CollectingVisitor<ResourcePaths, IconResource, ResourceVisitingException>() {

    override fun visit(element: ResourcePaths): Result<Unit, ResourceVisitingException> =
        try {
            IconResource(
                name = element.resourceFile.nameWithoutExtension,
                element.subPathToResource,
                element.relativeResourcePath
            ).add() // gathering all filenames without any filtration
            Result.Success(Unit)
        } catch (exception: Exception) {
            Result.Failure(ResourceVisitingException(exception))
        }

}
