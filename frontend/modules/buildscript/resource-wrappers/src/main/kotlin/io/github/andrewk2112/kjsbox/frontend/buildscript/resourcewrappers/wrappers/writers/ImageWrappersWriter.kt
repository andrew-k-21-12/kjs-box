package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.wrappers.writers

import io.github.andrewk2112.utility.common.extensions.ensureDirectoryExistsOrThrow
import io.github.andrewk2112.utility.common.extensions.joinWithPath
import io.github.andrewk2112.utility.common.extensions.writeTo
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.models.ImageResource
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.wrappers.templates.ImageWrapperTemplates
import io.github.andrewk2112.utility.gradle.extensions.toUniversalPathString
import io.github.andrewk2112.utility.string.formats.cases.CamelCase
import io.github.andrewk2112.utility.string.formats.cases.KebabCase
import io.github.andrewk2112.utility.string.formats.changeFormat
import io.github.andrewk2112.utility.string.formats.other.PackageName
import io.github.andrewk2112.utility.string.formats.other.UniversalPath
import io.github.andrewk2112.utility.string.extensions.modifyIfNotEmpty
import java.io.File

/**
 * Inflates and writes image wrappers and related sources to files.
 */
internal class ImageWrappersWriter(
    private val imageWrapperTemplates: ImageWrapperTemplates = ImageWrapperTemplates(),
) {

    // API.

    /**
     * Does all preliminary preparations and checks,
     * writes an [imageResource] wrapper into the [allWrappersOutDirectory] with the [basePackageName].
     */
    @Throws(WrapperWritingException::class)
    internal fun writeWrapper(
        allWrappersOutDirectory: File,
        basePackageName: String,
        imageResource: ImageResource
    ) {
        try {

            // Preparing a destination directory to write a wrapper to, making sure it exists.
            val wrapperOutDirectory = allWrappersOutDirectory
                .joinWithPath(basePackageName.changeFormat(PackageName, UniversalPath))
                .joinWithPath(imageResource.relativePath)
                .also {
                    it.ensureDirectoryExistsOrThrow(
                        "Can not create the output directory for an image wrapper: ${it.absolutePath}"
                    )
                }

            // Preparing a target package name for the image wrapper.
            val wrapperPackageName = basePackageName +
                                     imageResource.relativePath.modifyIfNotEmpty {
                                         ".${it.changeFormat(UniversalPath, PackageName)}"
                                     }

            // Preparing the name of the image wrapper.
            val objectName = generateSimpleImageObjectName(imageResource.name)

            // Writing the customized image wrapper.
            imageWrapperTemplates
                .inflateSimpleImage(
                    wrapperPackageName,
                    objectName,
                    imageResource.extension,
                    imageResource.imageSize.width,
                    imageResource.imageSize.height,
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
    private fun generateSimpleImageObjectName(name: String): String = name.changeFormat(KebabCase, CamelCase) + "Image"

}
