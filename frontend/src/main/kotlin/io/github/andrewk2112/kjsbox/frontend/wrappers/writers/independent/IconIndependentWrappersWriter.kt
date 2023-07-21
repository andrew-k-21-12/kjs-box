package io.github.andrewk2112.kjsbox.frontend.wrappers.writers.independent

import io.github.andrewk2112.kjsbox.frontend.extensions.joinCapitalized
import io.github.andrewk2112.kjsbox.frontend.extensions.writeTo
import io.github.andrewk2112.kjsbox.frontend.extensions.joinWithPath
import io.github.andrewk2112.kjsbox.frontend.extensions.toUniversalPathString
import io.github.andrewk2112.kjsbox.frontend.models.IconResource
import io.github.andrewk2112.kjsbox.frontend.wrappers.templates.IconWrapperTemplate
import java.io.File

/**
 * Inflates and writes icon wrappers to files.
 */
internal class IconIndependentWrappersWriter(
    private val iconWrapperTemplate: IconWrapperTemplate = IconWrapperTemplate(),
) : IndependentWrappersWriter<IconResource>() {

    // Implementation.

    @Throws(Exception::class)
    override fun performWrapperWriting(wrapperOutDirectory: File, wrapperPackageName: String, resource: IconResource) {

        // Preparing the element name.
        val elementName = resource.generateElementName()

        // Writing the icon wrapper.
        iconWrapperTemplate
            .inflate(
                wrapperPackageName,
                elementName,
                resource.relativeIconPath.toUniversalPathString()
            )
            .writeTo(
                wrapperOutDirectory.joinWithPath("$elementName.kt")
            )

    }



    // Private.

    /**
     * Generates a particular element name for the [IconResource].
     */
    private fun IconResource.generateElementName(): String = name.split("-").joinCapitalized().decapitalize() + "Icon"

}