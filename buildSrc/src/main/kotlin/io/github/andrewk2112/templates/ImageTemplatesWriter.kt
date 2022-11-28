package io.github.andrewk2112.templates

import io.github.andrewk2112.extensions.ensureDirectoryExistsOrThrow
import io.github.andrewk2112.extensions.joinCapitalized
import io.github.andrewk2112.extensions.toUniversalPathString
import io.github.andrewk2112.extensions.writeTo
import io.github.andrewk2112.models.ImageResource
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

/**
 * Inflates and writes to files image wrappers-related classes.
 */
internal class ImageTemplatesWriter(
    private val simpleTemplatesInflater: SimpleTemplatesInflater = SimpleTemplatesInflater()
) {

    // API.

    /**
     * Performs checks and writes all required base interfaces with the [packageName] into the [outDir].
     */
    @Throws(IOException::class, FileNotFoundException::class, SecurityException::class)
    internal fun writeBaseInterfaces(outDir: File, packageName: String) {

        // Making sure the destination dir exist.
        outDir.ensureDirectoryExistsOrThrow(
            "Can not create the output directory for the base interfaces: ${outDir.absolutePath}"
        )

        simpleTemplatesInflater.apply {

            // Writing the sealed interface common for all image wrappers.
            inflate("/template_image.txt", packageName).writeTo(File(outDir, "Image.kt"))

            // Writing the interface representing simple images without variants.
            inflate("/template_simple_image.txt", packageName).writeTo(File(outDir, "SimpleImage.kt"))

        }

    }

    /**
     * Performs checks and writes an image wrapper into the [outDir]
     * using the [packageName] with all data from the [imageResource].
     */
    @Throws(IOException::class, FileNotFoundException::class, SecurityException::class)
    internal fun writeSimpleImageObject(outDir: File, packageName: String, imageResource: ImageResource) {

        // Making sure the destination dir exist.
        outDir.ensureDirectoryExistsOrThrow(
            "Can not create the output directory for an image wrapper: ${outDir.absolutePath}"
        )

        // Preparing the name of the image wrapper.
        val objectName = generateSimpleImageObjectName(imageResource.relativePath, imageResource.name)

        // Writing the customized image wrapper.
        simpleTemplatesInflater
            .inflate(
                "/template_simple_image_impl.txt",
                packageName,
                objectName,
                imageResource.imageSize.width,
                imageResource.imageSize.height,
                imageResource.relativeImagePath.toUniversalPathString()
            )
            .writeTo(File(outDir, "$objectName.kt"))

    }



    // Private.

    /**
     * Generates an object name for the particular image wrapper.
     *
     * @param relativeSubPath A path to be included into the name.
     * @param name            A base name, should be in kebab-case.
     */
    private fun generateSimpleImageObjectName(relativeSubPath: String, name: String): String =
        relativeSubPath.split("/").joinCapitalized() + name.split("-").joinCapitalized() + "Image"

}
