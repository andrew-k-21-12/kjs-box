package io.github.andrewk2112.utility

import java.awt.Dimension
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import javax.imageio.ImageIO
import javax.imageio.stream.FileImageInputStream

/**
 * Reads images' descriptions without loading their files entirely into memory.
 * */
internal class ImageMetadataReader {

    /**
     * Reads sizes ([Dimension]) of an [image].
     * */
    @Throws(SecurityException::class, FileNotFoundException::class, IOException::class, IllegalArgumentException::class)
    internal fun readDimension(image: File): Dimension {
        val imageReaders = ImageIO.getImageReadersBySuffix(image.extension)
        while (imageReaders.hasNext()) {
            val imageReader = imageReaders.next()
            var inputStream: FileImageInputStream? = null
            try {
                inputStream = FileImageInputStream(image)
                imageReader.input = inputStream
                return Dimension(
                    imageReader.getWidth(imageReader.minIndex),
                    imageReader.getHeight(imageReader.minIndex)
                )
            } finally {
                imageReader.dispose()
                inputStream?.close()
            }
        }
        throw IllegalArgumentException("Not a known image file: ${image.absolutePath}")
    }

}
