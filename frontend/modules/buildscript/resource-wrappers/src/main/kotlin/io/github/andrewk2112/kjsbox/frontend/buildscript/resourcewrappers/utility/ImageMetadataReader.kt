package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.imagemetadata

import io.github.andrewk2112.utility.common.utility.Result
import java.awt.Dimension
import java.io.File
import javax.imageio.ImageIO
import javax.imageio.stream.FileImageInputStream

/**
 * Works with basic image types supported by [ImageIO].
 */
internal class RegularImageMetadataReader : ImageMetadataReader {

    override fun readDimension(image: File): Result<Dimension, Exception> =
        Result.runCatchingTypedException {
            val imageReaders = ImageIO.getImageReadersBySuffix(image.extension)
            while (imageReaders.hasNext()) {
                val imageReader = imageReaders.next()
                try {
                    FileImageInputStream(image).use {
                        imageReader.input = it
                        return@runCatchingTypedException Dimension(
                            imageReader.getWidth(imageReader.minIndex),
                            imageReader.getHeight(imageReader.minIndex)
                        )
                    }
                } finally {
                    imageReader.dispose()
                }
            }
            throw IllegalArgumentException(
                "An image file is not supported by ${ImageIO::class.simpleName}: ${image.absolutePath}"
            )
        }

}
