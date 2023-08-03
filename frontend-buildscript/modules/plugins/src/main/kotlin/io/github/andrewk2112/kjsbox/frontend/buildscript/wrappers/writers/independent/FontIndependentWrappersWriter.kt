package io.github.andrewk2112.kjsbox.frontend.buildscript.wrappers.writers.independent

import io.github.andrewk2112.kjsbox.frontend.buildscript.extensions.toUniversalPathString
import io.github.andrewk2112.kjsbox.frontend.buildscript.extensions.joinWithPath
import io.github.andrewk2112.kjsbox.frontend.buildscript.models.FontResource
import io.github.andrewk2112.kjsbox.frontend.buildscript.wrappers.templates.FontWrapperTemplates
import io.github.andrewk2112.kjsbox.frontend.buildscript.wrappers.templates.FontWrapperTemplates.FontVariant
import org.gradle.configurationcache.extensions.capitalized
import java.io.File

/**
 * Inflates and writes font wrappers to files.
 */
internal class FontIndependentWrappersWriter(
    private val fontWrapperTemplates: FontWrapperTemplates = FontWrapperTemplates()
) : IndependentWrappersWriter<FontResource>() {

    // Implementation.

    @Throws(Exception::class)
    override fun performWrapperWriting(wrapperOutDirectory: File, wrapperPackageName: String, resource: FontResource) {

        // Preparing the class name.
        val className = resource.generateClassName()

        // Collecting all font variants to generate wrappers for.
        val fontVariants = resource.variants.map {
            FontVariant(
                variantPropertyName     = it.variantName.decapitalize(),
                fontFamily              = resource.fontFamily,
                variantName             = it.variantName,
                referencePropertyName   = generateReferencePropertyName(resource.fontFamily, it),
                fontFormat              = it.format,
                fontWeight              = it.fontWeight,
                fontStyle               = it.fontStyle,
                fontFamilyWithFallbacks = listOf(resource.fontFamily, *it.fallbackFontFamilies).joinToString(),
                relativeFontPath        = it.relativeFontPath.toUniversalPathString(),
            )
        }

        // Writing a wrapper for fonts of all available variants.
        wrapperOutDirectory.joinWithPath("$className.kt")
                           .writeText(
                               fontWrapperTemplates.inflateFontStylesObject(wrapperPackageName, className, fontVariants)
                           )

    }



    // Private.

    /**
     * Generates a particular class name for a [FontResource].
     */
    private fun FontResource.generateClassName(): String = fontFamily + "FontStyles"

    /**
     * Generates a reference property name using the [fontFamily] for pointing to the [fontVariant].
     */
    private fun generateReferencePropertyName(fontFamily: String, fontVariant: FontResource.Variant): String =
        fontFamily.decapitalize() + fontVariant.variantName + fontVariant.format.capitalized() + "FontReference"

    /** Picks the right font weight constant for a [FontResource.Variant]. */
    private val FontResource.Variant.fontWeight: String
        get() = when (variantName) {
            "Light" -> "FontWeight.w300"
            else    -> "FontWeight.normal"
        }

    /** Picks the right font style constant for a [FontResource.Variant]. */
    private val FontResource.Variant.fontStyle: String
        get() = when (variantName) { else -> "FontStyle.normal" }

}
