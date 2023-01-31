package io.github.andrewk2112.templates.wrappers.independent

import io.github.andrewk2112.extensions.*
import io.github.andrewk2112.extensions.joinCapitalized
import io.github.andrewk2112.extensions.toUniversalPathString
import io.github.andrewk2112.models.FontResource
import io.github.andrewk2112.templates.SimpleTemplatesInflater
import java.io.File
import java.io.IOException
import kotlin.text.StringBuilder

/**
 * Inflates and writes font wrappers to files.
 */
internal class FontIndependentWrappersWriter(
    private val simpleTemplatesInflater: SimpleTemplatesInflater = SimpleTemplatesInflater()
) : IndependentWrappersWriter<FontResource>() {

    // Implementation.

    @Throws(Exception::class)
    override fun performWrapperWriting(wrapperOutDirectory: File, wrapperPackageName: String, resource: FontResource) {

        // Preparing the class name.
        val className = resource.generateClassName()

        // Generating the code for all variants of the font.
        val stylePropertiesBuilder     = StringBuilder()
        val referencePropertiesBuilder = StringBuilder()
        for (fontVariant in resource.variants) {
            val referencePropertyName = generateReferencePropertyName(
                wrapperPackageName,
                resource.fontFamily,
                fontVariant
            )
            stylePropertiesBuilder.append(
                generateStyleProperty(resource.fontFamily, referencePropertyName, fontVariant)
            )
            referencePropertiesBuilder.append(
                generateReferenceProperty(referencePropertyName, fontVariant.relativeFontPath)
            )
        }

        // Writing the font wrapper with all prepared properties.
        File(wrapperOutDirectory, "$className.kt").writeText(
            simpleTemplatesInflater.inflate(
                "/templates/font_styles.txt",
                wrapperPackageName,
                className,
                stylePropertiesBuilder,
                referencePropertiesBuilder
            )
        )

    }



    // Private.

    /**
     * Generates a particular class name for the [FontResource].
     */
    private fun FontResource.generateClassName(): String = fontFamily + "FontStyles"

    /**
     * Generates a unique property name using the [fullPackageName] and [fontFamily] for pointing to the [fontVariant].
     */
    private fun generateReferencePropertyName(
        fullPackageName: String,
        fontFamily: String,
        fontVariant: FontResource.Variant
    ): String = fontVariant.format +
                "QueryFor" +
                fullPackageName.split(".").joinCapitalized() +
                fontFamily +
                fontVariant.variantName

    /**
     * Inflates a font style property from its template, filling it with all required data.
     *
     * @param fontFamily            Font family.
     * @param referencePropertyName The name of a variable to point to the font resource.
     * @param fontVariant           All data about the [FontResource.Variant] to be inflated.
     */
    @Throws(IOException::class)
    private fun generateStyleProperty(
        fontFamily: String,
        referencePropertyName: String,
        fontVariant: FontResource.Variant,
    ): String {

        // Preparing configs to be inflated.
        val variableName = fontVariant.variantName.decapitalize()
        val fontWeight   = when (fontVariant.variantName) {
            "Light" -> "FontWeight.w300"
            else    -> "FontWeight.normal"
        }
        val fontStyle               = when (fontVariant.variantName) { else -> "FontStyle.normal" }
        val fontFamilyWithFallbacks = listOf(fontFamily, *fontVariant.fallbackFontFamilies).joinToString()

        // Inflating with all configs.
        return simpleTemplatesInflater.inflate(
            "/templates/font_styles_style.txt",
            variableName,
            fontFamily,
            fontVariant.variantName,
            referencePropertyName,
            fontVariant.format,
            fontWeight,
            fontStyle,
            fontFamilyWithFallbacks
        )

    }

    /**
     * Inflates a property named by the [propertyName] and pointing to the [relativeFontPath].
     */
    @Throws(IOException::class)
    private fun generateReferenceProperty(propertyName: String, relativeFontPath: String): String =
        simpleTemplatesInflater.inflate(
            "/templates/font_styles_reference.txt",
            relativeFontPath.toUniversalPathString(),
            propertyName
        )

}
