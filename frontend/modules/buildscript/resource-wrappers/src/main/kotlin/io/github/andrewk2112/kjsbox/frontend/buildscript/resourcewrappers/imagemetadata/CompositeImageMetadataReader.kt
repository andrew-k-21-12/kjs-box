package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.imagemetadata

import io.github.andrewk2112.utility.common.utility.Result
import java.awt.Dimension
import java.io.File

/**
 * Combines multiple [readers] and returns a result of the first successful one.
 */
internal class CompositeImageMetadataReader(private vararg val readers: ImageMetadataReader) : ImageMetadataReader {

    override fun readDimension(image: File): Result<Dimension, Exception> {
        readers.forEach { reader ->
            reader.readDimension(image).getOrNull()?.let { return it }
        }
        return Result.Failure(
            IllegalArgumentException(
                "No proper ${ImageMetadataReader::class.simpleName} to process an image: ${image.absolutePath}"
            )
        )
    }

}
