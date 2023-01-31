package io.github.andrewk2112.resources.visitors

import io.github.andrewk2112.extensions.subList
import io.github.andrewk2112.models.FontResource
import io.github.andrewk2112.resources.ResourcePaths
import io.github.andrewk2112.resources.ResourceVisitingException
import io.github.andrewk2112.utility.CollectingVisitor
import io.github.andrewk2112.utility.Result

/**
 * A [CollectingVisitor] to gather all [FontResource] metadata.
 */
internal class FontResourceVisitor : CollectingVisitor<ResourcePaths, FontResource, ResourceVisitingException>() {

    override fun visit(element: ResourcePaths): Result<Unit, ResourceVisitingException> {
        try {

            // It's possible to define any number of fallback font families split by the underscores in the font's name.
            val nameParts = element.resourceFile.nameWithoutExtension.split("_")

            // The first part should only be constructed of two elements separated with a dash:
            // other naming formats will not be processed.
            val (fontFamily, variantName) = nameParts.firstOrNull()
                                                    ?.split("-")
                                                    ?.takeIf { it.size == 2 } ?: return Result.Success(Unit)

            // Preparing the font variant to be assigned to the font family.
            val fontVariant = FontResource.Variant(
                variantName,
                format = element.resourceFile.extension,
                element.relativeResourcePath,
                fallbackFontFamilies = nameParts.subList(1).toTypedArray(),
            )

            // Completing the resource visiting.
            addOrReplace({ it.fontFamily == fontFamily && it.relativePath == element.subPathToResource }) { foundFont ->
                if (foundFont == null) { // adding a new font resource if the font family is met for the first time
                    FontResource(fontFamily, element.subPathToResource, listOf(fontVariant))
                } else {                 // appending a new variant to the font family has been encountered already
                    FontResource(fontFamily, element.subPathToResource, foundFont.variants + fontVariant)
                }
            }

            return Result.Success(Unit)

        } catch (exception: Exception) {
            return Result.Failure(ResourceVisitingException(exception))
        }
    }

}
