package io.github.andrewk2112.templates.wrappers.independent

import io.github.andrewk2112.extensions.*
import io.github.andrewk2112.extensions.joinCapitalized
import io.github.andrewk2112.extensions.writeTo
import io.github.andrewk2112.models.IconResource
import io.github.andrewk2112.templates.SimpleTemplatesInflater
import java.io.File

/**
 * Inflates and writes icon wrappers to files.
 */
internal class IconIndependentWrappersWriter(
    private val simpleTemplatesInflater: SimpleTemplatesInflater = SimpleTemplatesInflater()
) : IndependentWrappersWriter<IconResource>() {

    // Implementation.

    @Throws(Exception::class)
    override fun performWrapperWriting(wrapperOutDirectory: File, wrapperPackageName: String, resource: IconResource) {

        // Preparing the element name.
        val elementName = resource.generateElementName()

        // Writing the icon wrapper.
        simpleTemplatesInflater
            .inflate(
                "/templates/icon.txt",
                wrapperPackageName,
                elementName,
                resource.relativeIconPath.toUniversalPathString()
            )
            .writeTo(File(wrapperOutDirectory, "$elementName.kt"))

    }



    // Private.

    /**
     * Generates a particular element name for the [IconResource].
     */
    private fun IconResource.generateElementName(): String = name.split("-").joinCapitalized().decapitalize() + "Icon"

}
