package io.github.andrewk2112.kjsbox.frontend.buildscript.wrappers.writers

import io.github.andrewk2112.kjsbox.frontend.buildscript.extensions.*
import io.github.andrewk2112.kjsbox.frontend.buildscript.models.ImageResource
import io.github.andrewk2112.kjsbox.frontend.buildscript.wrappers.templates.ImageWrapperTemplates
import java.io.File

/**
 * Inflates and writes image wrappers and related sources to files.
 */
internal class ImageWrappersWriter(
    private val imageWrapperTemplates: ImageWrapperTemplates = ImageWrapperTemplates(),
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

            imageWrapperTemplates.apply {

                // Writing the sealed interface common for all image wrappers.
                inflateImageInterface(packageName)
                    .writeTo(baseInterfacesOutDirectory.joinWithPath("Image.kt"))

                // Writing the interface representing simple images without variants.
                inflateSimpleImageInterface(packageName)
                    .writeTo(baseInterfacesOutDirectory.joinWithPath("SimpleImage.kt"))

            }

        } catch (exception: Exception) {
            throw SupportingSourceWritingException(exception)
        }
    }

    /**
     * Does all preliminary preparations and checks,
     * writes an [imageResource] wrapper dependent on the [interfacesPackageName]
     * into the [allWrappersOutDirectory] with the [basePackageName].
     */
    @Throws(WrapperWritingException::class)
    internal fun writeWrapper(
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

            // Preparing a target package name for the image wrapper.
            val wrapperPackageName = basePackageName +
                                     imageResource.relativePath.modifyIfNotEmpty { ".${it.toValidPackage()}" }

            // Preparing the name of the image wrapper.
            val objectName = generateSimpleImageObjectName(imageResource.name)

            // Writing the customized image wrapper.
            imageWrapperTemplates
                .inflateSimpleImage(
                    wrapperPackageName,
                    interfacesPackageName,
                    objectName,
                    imageResource.imageSize.width,
                    imageResource.imageSize.height,
                    objectName.decapitalize(),
                    imageResource.relativeImagePath.toUniversalPathString()
                )
                .writeTo(wrapperOutDirectory.joinWithPath("$objectName.kt"))

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
