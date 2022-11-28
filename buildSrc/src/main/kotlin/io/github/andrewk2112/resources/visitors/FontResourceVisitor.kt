package io.github.andrewk2112.resources.visitors

import io.github.andrewk2112.models.FontResource
import org.apache.commons.io.FilenameUtils
import java.io.File
import java.nio.file.InvalidPathException
import java.nio.file.Path

/**
 * A [ResourceVisitor] to collect all [FontResource] metadata.
 */
internal class FontResourceVisitor : ResourceVisitor {

    // API.

    @Throws(InvalidPathException::class, IllegalArgumentException::class)
    override fun visit(rootResourcesPath: Path, targetResourcesPath: Path, resourceFile: File) {

        // Preparing names: name parts of all font resources should be divided with dashes,
        // resources named with other format will be skipped.
        val (fontFamily, variantName) = resourceFile.nameWithoutExtension.split("-")
                                                                         .takeIf { it.size == 2 } ?: return
        val format                    = resourceFile.extension

        // Preparing relative paths.
        val absoluteResourcePath        = resourceFile.toPath()
        val rootRelativePath            = rootResourcesPath.relativize(absoluteResourcePath).toString()
        val targetRelativePath          = targetResourcesPath.relativize(absoluteResourcePath).toString()
        val pathFromTargetDirToResource = FilenameUtils.getPathNoEndSeparator(targetRelativePath)

        // Preparing a font variant to be assigned to the family.
        val fontVariant = FontResource.Variant(variantName, format, rootRelativePath)

        // Completing the resource visiting.
        appendFontVariant(fontFamily, pathFromTargetDirToResource, fontVariant)

    }

    /**
     * Clears all previously collected metadata about [FontResource]s.
     */
    internal fun reset() = _fontResources.clear()

    /** Provides collected metadata for all [FontResource]s. */
    internal val fontResources: List<FontResource> get() = _fontResources
    private val _fontResources = mutableListOf<FontResource>()



    // Private.

    /**
     * Adds a new [fontVariant] to the list of visited [fontResources].
     */
    private fun appendFontVariant(fontFamily: String, relativePath: String, fontVariant: FontResource.Variant) {

        // Trying to find the font of the same family group.
        val similarFont = _fontResources.find { it.fontFamily == fontFamily && it.relativePath == relativePath }

        // If such font is not found - adding a new font resource.
        if (similarFont == null) {
            _fontResources.add(
                FontResource(fontFamily, relativePath, listOf(fontVariant))
            )
        }

        // When the font of the same group already exists - just appending a new variant to it.
        else {
            _fontResources[_fontResources.indexOf(similarFont)] =
                FontResource(fontFamily, relativePath, similarFont.variants + fontVariant)
        }

    }

}
