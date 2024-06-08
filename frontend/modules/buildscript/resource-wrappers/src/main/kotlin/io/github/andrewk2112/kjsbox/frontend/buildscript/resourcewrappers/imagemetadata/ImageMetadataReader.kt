package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.imagemetadata

import io.github.andrewk2112.utility.common.utility.Result
import java.awt.Dimension
import java.io.File

/**
 * A protocol to read images' metadata without loading entire files into memory.
 */
internal interface ImageMetadataReader {

    /**
     * Reads width and height of a provided [image].
     */
    fun readDimension(image: File): Result<Dimension, Exception>

}
