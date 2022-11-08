package io.github.andrewk2112.templates

import io.github.andrewk2112.extensions.ensureDirectoryExistsOrThrow
import io.github.andrewk2112.extensions.joinCapitalized
import io.github.andrewk2112.extensions.slashesToDots
import io.github.andrewk2112.extensions.toUniversalPathString
import io.github.andrewk2112.models.FontResource
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import kotlin.text.StringBuilder

/**
 * Inflates and writes to files font wrappers-related classes.
 * */
internal class FontTemplatesWriter(
    private val simpleTemplatesInflater: SimpleTemplatesInflater = SimpleTemplatesInflater()
) {

    // API.

    /**
     * Performs checks and writes a font wrapper into the [outDir]
     * using the [basePackageName] with all data from the [fontResource].
     * This font wrapper will be configured to fallback into [fallbackFontFamilies]
     * when the target font can't be fetched.
     * */
    @Throws(IOException::class, FileNotFoundException::class, SecurityException::class)
    internal fun writeFontStylesObject(
        outDir: File,
        basePackageName: String,
        fontResource: FontResource,
        vararg fallbackFontFamilies: String
    ) {

        // Making sure the target output directory exists.
        outDir.ensureDirectoryExistsOrThrow(
            "Can not create the output directory for a font wrapper: ${outDir.absolutePath}"
        )

        // Preparing the class and package names.
        val fullPackageName = basePackageName + "." + fontResource.relativePath.slashesToDots()
        val className       = generateClassName(fontResource)

        // Generating the code for all variants of the font.
        val stylePropertiesBuilder     = StringBuilder()
        val referencePropertiesBuilder = StringBuilder()
        for (fontVariant in fontResource.variants) {
            val referencePropertyName = generateReferencePropertyName(
                fullPackageName,
                fontResource.fontFamily,
                fontVariant
            )
            stylePropertiesBuilder.append(
                generateStyleProperty(fontResource.fontFamily, referencePropertyName, fontVariant, fallbackFontFamilies)
            )
            referencePropertiesBuilder.append(
                generateReferenceProperty(referencePropertyName, fontVariant.relativeFontPath)
            )
        }

        // Writing the font wrapper with all prepared properties.
        File(outDir, "$className.kt").writeText(
            simpleTemplatesInflater.inflate(
                "/template_font_styles.txt",
                fullPackageName,
                className,
                stylePropertiesBuilder,
                referencePropertiesBuilder
            )
        )

    }



    // Private.

    /**
     * Generates a particular class name for the [fontResource].
     * */
    private fun generateClassName(fontResource: FontResource): String = fontResource.fontFamily + "FontStyles"

    /**
     * Generates a unique property name using the [fullPackageName] and [fontFamily] for pointing to the [fontVariant].
     * */
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
     * @param fallbackFontFamilies  Font families to be used if the font resource is unavailable for some reason.
     * */
    @Throws(IOException::class)
    private fun generateStyleProperty(
        fontFamily: String,
        referencePropertyName: String,
        fontVariant: FontResource.Variant,
        fallbackFontFamilies: Array<out String>
    ): String {

        // Preparing configs to be inflated.
        val variableName = fontVariant.variantName.decapitalize()
        val fontWeight   = when (fontVariant.variantName) {
            "Light" -> "FontWeight.w300"
            else    -> "FontWeight.normal"
        }
        val fontStyle               = when (fontVariant.variantName) { else -> "FontStyle.normal" }
        val fontFamilyWithFallbacks = listOf(fontFamily, *fallbackFontFamilies).joinToString()

        // Inflating with all configs.
        return simpleTemplatesInflater.inflate(
            "/template_font_styles_style.txt",
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
     * */
    @Throws(IOException::class)
    private fun generateReferenceProperty(propertyName: String, relativeFontPath: String): String =
        simpleTemplatesInflater.inflate(
            "/template_font_styles_reference.txt",
            relativeFontPath.toUniversalPathString(),
            propertyName
        )

}
