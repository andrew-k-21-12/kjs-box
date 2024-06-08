package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.imagemetadata

import java.awt.Dimension
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class WebpImageMetadataReaderTest {

    @Test
    @Throws(Throwable::class)
    fun testReadDimension() {
        val testImagesWithExpectedDimensions = arrayOf(
            "VP8.webp"  to Dimension(3, 777),
            "VP8L.webp" to Dimension(2, 82),
            "VP8X.webp" to Dimension(2, 28),
        )
        WebpImageMetadataReader().run {
            testImagesWithExpectedDimensions.forEach { (resourceName, expectedDimension) ->
                assertEquals(
                    expectedDimension,
                    readDimension(getResourceAsFile(resourceName)).getOrThrow()
                )
            }
        }
    }

    @Throws(IllegalArgumentException::class, SecurityException::class)
    private fun getResourceAsFile(resourceName: String): File =
        javaClass.classLoader.getResource(resourceName)?.file?.run(::File)
            ?: throw IllegalArgumentException("No resource found: $resourceName")

}
