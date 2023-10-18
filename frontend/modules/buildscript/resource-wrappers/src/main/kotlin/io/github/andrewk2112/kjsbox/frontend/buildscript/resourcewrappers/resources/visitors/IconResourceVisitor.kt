package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.resources.visitors

import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.models.IconResource
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.resources.ResourcePaths
import io.github.andrewk2112.utility.common.utility.CollectingVisitor
import io.github.andrewk2112.utility.common.utility.Result

/**
 * A [CollectingVisitor] to gather all [IconResource] metadata.
 */
internal class IconResourceVisitor : CollectingVisitor<ResourcePaths, IconResource, IconResourceVisitor.Exception>() {

    // Utility.

    /**
     * When any kind of failure has happened.
     */
    internal class Exception(cause: Throwable) : kotlin.Exception(cause)



    // Implementation.

    override fun visit(element: ResourcePaths): Result<Unit, Exception> =
        try {
            IconResource(
                name = element.resourceFile.nameWithoutExtension,
                element.subPathToResource,
                element.relativeResourcePath
            ).add() // gathering all filenames without any filtration
            Result.Success(Unit)
        } catch (exception: kotlin.Exception) {
            Result.Failure(Exception(exception))
        }

}
