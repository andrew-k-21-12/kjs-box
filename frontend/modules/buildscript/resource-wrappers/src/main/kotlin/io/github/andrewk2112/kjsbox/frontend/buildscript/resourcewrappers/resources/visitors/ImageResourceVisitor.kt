package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.resources.visitors

import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.models.ImageResource
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.resources.ResourcePaths
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.utility.ImageMetadataReader
import io.github.andrewk2112.utility.common.utility.CollectingVisitor
import io.github.andrewk2112.utility.common.utility.Result

/**
 * A [CollectingVisitor] to gather all [ImageResource] metadata.
 */
internal class ImageResourceVisitor(
    private val imageMetadataReader: ImageMetadataReader = ImageMetadataReader()
) : CollectingVisitor<ResourcePaths, ImageResource, ImageResourceVisitor.Exception>() {

    // Utility.

    /**
     * Any failure can happen during the [visit]ing.
     */
    internal sealed class Exception(cause: Throwable) : kotlin.Exception(cause) {

        /**
         * When reading an image's metadata has failed.
         */
        internal class ImageMetadataReadingException(cause: Throwable) : Exception(cause)

        /**
         * When an [ImageResource] has failed to be created.
         */
        internal class ImageResourceCreationException(cause: Throwable) : Exception(cause)

    }



    // Implementation.

    override fun visit(element: ResourcePaths): Result<Unit, Exception> {
        val imageDimension = try {
            imageMetadataReader.readDimension(element.resourceFile)
        } catch (exception: kotlin.Exception) {
            return Result.Failure(Exception.ImageMetadataReadingException(exception))
        }
        return try {
            ImageResource(
                name = element.resourceFile.nameWithoutExtension,
                element.subPathToResource,
                element.relativeResourcePath,
                imageDimension
            ).add()
            Result.Success(Unit)
        } catch (exception: kotlin.Exception) {
            Result.Failure(Exception.ImageResourceCreationException(exception))
        }
    }

}
