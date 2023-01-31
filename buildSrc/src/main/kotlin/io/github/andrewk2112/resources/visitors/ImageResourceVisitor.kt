package io.github.andrewk2112.resources.visitors

import io.github.andrewk2112.models.ImageResource
import io.github.andrewk2112.resources.ResourcePaths
import io.github.andrewk2112.resources.ResourceVisitingException
import io.github.andrewk2112.utility.ImageMetadataReader
import io.github.andrewk2112.utility.CollectingVisitor
import io.github.andrewk2112.utility.Result

/**
 * A [CollectingVisitor] to gather all [ImageResource] metadata.
 */
internal class ImageResourceVisitor(
    private val imageMetadataReader: ImageMetadataReader = ImageMetadataReader()
) : CollectingVisitor<ResourcePaths, ImageResource, ResourceVisitingException>() {

    override fun visit(element: ResourcePaths): Result<Unit, ResourceVisitingException> =
        try {
            ImageResource(
                name = element.resourceFile.nameWithoutExtension,
                element.subPathToResource,
                element.relativeResourcePath,
                imageSize = imageMetadataReader.readDimension(element.resourceFile)
            ).add()
            Result.Success(Unit)
        } catch (exception: Exception) {
            Result.Failure(ResourceVisitingException(exception))
        }

}
