package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.resources.visitors

import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.imagemetadata.CompositeImageMetadataReader
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.imagemetadata.ImageMetadataReader
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.models.ImageResource
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.resources.ResourcePaths
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.imagemetadata.RegularImageMetadataReader
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.imagemetadata.WebpImageMetadataReader
import io.github.andrewk2112.utility.common.utility.CollectingVisitor
import io.github.andrewk2112.utility.common.utility.Result
import io.github.andrewk2112.utility.common.utility.Result.Companion.getOrElse

/**
 * A [CollectingVisitor] to gather all [ImageResource] metadata.
 */
internal class ImageResourceVisitor(
    private val imageMetadataReader: ImageMetadataReader = CompositeImageMetadataReader(
        RegularImageMetadataReader(),
        WebpImageMetadataReader(),
    )
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
        val imageDimension = imageMetadataReader.readDimension(element.resourceFile)
                                                .getOrElse {
                                                    return Result.Failure(Exception.ImageMetadataReadingException(it))
                                                }
        return try {
            ImageResource(
                name      = element.resourceFile.nameWithoutExtension,
                extension = element.resourceFile.extension,
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
