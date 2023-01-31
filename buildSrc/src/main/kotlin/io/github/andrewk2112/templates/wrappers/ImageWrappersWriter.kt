package io.github.andrewk2112.templates.wrappers

import io.github.andrewk2112.extensions.*
import io.github.andrewk2112.extensions.joinCapitalized
import io.github.andrewk2112.extensions.toUniversalPathString
import io.github.andrewk2112.extensions.writeTo
import io.github.andrewk2112.models.ImageResource
import io.github.andrewk2112.templates.SimpleTemplatesInflater
import java.io.File

/**
 * Inflates and writes image wrappers and related sources to files.
 */
internal class ImageWrappersWriter(
    private val simpleTemplatesInflater: SimpleTemplatesInflater = SimpleTemplatesInflater()
) {

    // API.

    /**
     * Performs preliminary preparations and checks,
     * writes required base interfaces into [outDirectory] with [packageName].
     */
    @Throws(SupportingSourceWritingException::class)
    internal fun writeBaseInterfaces(outDirectory: File, packageName: String) {
        try {

            // Where to write base interfaces - making sure this destination directory exists.
            val baseInterfacesOutDirectory = outDirectory
                .joinWithPath(packageName.dotsToSlashes())
                .also {
                    it.ensureDirectoryExistsOrThrow(
                        "Can not create the output directory for image basic interfaces: ${it.absolutePath}"
                    )
                }

            simpleTemplatesInflater.apply {

                // Writing the sealed interface common for all image wrappers.
                inflate("/templates/image.txt", packageName)
                    .writeTo(File(baseInterfacesOutDirectory, "Image.kt"))

                // Writing the interface representing simple images without variants.
                inflate("/templates/simple_image.txt", packageName)
                    .writeTo(File(baseInterfacesOutDirectory, "SimpleImage.kt"))

            }

        } catch (exception: Exception) {
            throw SupportingSourceWritingException(exception)
        }
    }

    /**
     * Does all preliminary preparations and checks,
     * writes an [imageResource] wrapper dependent on [interfacesPackageName]
     * into [allWrappersOutDirectory] with [basePackageName].
     */
    @Throws(WrapperWritingException::class)
    fun writeWrapper(
        allWrappersOutDirectory: File,
        basePackageName: String,
        interfacesPackageName: String,
        imageResource: ImageResource
    ) {
        try {

            // Preparing a destination directory to write a wrapper to, making sure it exists.
            val wrapperOutDirectory = allWrappersOutDirectory
                .joinWithPath(basePackageName.dotsToSlashes())
                .joinWithPath(imageResource.relativePath)
                .also {
                    it.ensureDirectoryExistsOrThrow(
                        "Can not create the output directory for an image wrapper: ${it.absolutePath}"
                    )
                }

            // Preparing the target package name for the image wrapper.
            val wrapperPackageName = basePackageName + "." + imageResource.relativePath.slashesToDots()

            // Preparing the name of the image wrapper.
            val objectName = generateSimpleImageObjectName(imageResource.name)

            // Writing the customized image wrapper.
            simpleTemplatesInflater
                .inflate(
                    "/templates/simple_image_impl.txt",
                    wrapperPackageName,
                    interfacesPackageName,
                    objectName,
                    imageResource.imageSize.width,
                    imageResource.imageSize.height,
                    imageResource.relativeImagePath.toUniversalPathString()
                )
                .writeTo(File(wrapperOutDirectory, "$objectName.kt"))

        } catch (exception: Exception) {
            throw WrapperWritingException(exception)
        }
    }



    // Private.

    /**
     * Generates an object name for a particular image wrapper.
     *
     * @param name A base name, should be in kebab-case.
     */
    private fun generateSimpleImageObjectName(name: String): String = name.split("-").joinCapitalized() + "Image"

}
