package io.github.andrewk2112.resources.visitors

import io.github.andrewk2112.extensions.subArray
import io.github.andrewk2112.models.FontResource
import io.github.andrewk2112.models.FontResource.Variant
import io.github.andrewk2112.resources.ResourcePaths
import io.github.andrewk2112.utility.CollectingVisitor
import io.github.andrewk2112.utility.Result

/**
 * A [CollectingVisitor] to gather all [FontResource] metadata.
 */
internal class FontResourceVisitor : CollectingVisitor<ResourcePaths, FontResource, FontResourceVisitor.Exception>() {

    // Utility.

    /**
     * Any failure can happen during the [visit]ing.
     */
    internal sealed class Exception(cause: Throwable) : kotlin.Exception(cause) {

        /**
         * When a [FontResource.Variant] has failed to be created.
         */
        internal class FontVariantCreationException(cause: Throwable) : Exception(cause)

        /**
         * When a [FontResource] has failed to be created.
         */
        internal class FontResourceCreationException(cause: Throwable) : Exception(cause)

    }



    // Implementation.

    override fun visit(element: ResourcePaths): Result<Unit, Exception> {

        // It's possible to define any number of fallback font families split by the underscores in the font's name.
        val nameParts = element.resourceFile.nameWithoutExtension.split("_")

        // The first part should only be constructed of two elements separated with a dash:
        // other naming formats will not be processed.
        val (fontFamily, variantName) = nameParts.firstOrNull()
                                                ?.split("-")
                                                ?.takeIf { it.size == 2 } ?: return Result.Success(Unit)

        // Preparing a font variant to be assigned to the font family.
        val fontVariant = try {
            Variant(variantName, element.resourceFile.extension, element.relativeResourcePath, *nameParts.subArray(1))
        } catch (exception: kotlin.Exception) {
            return Result.Failure(Exception.FontVariantCreationException(exception))
        }

        // Completing the resource visiting.
        return try {
            addOrReplace({ it.fontFamily == fontFamily && it.relativePath == element.subPathToResource }) { foundFont ->
                if (foundFont == null) { // adding a new font resource if the font family is met for the first time
                    FontResource(fontFamily, element.subPathToResource, listOf(fontVariant))
                } else {                 // appending a new variant to the font family has been encountered already
                    FontResource(fontFamily, element.subPathToResource, foundFont.variants + fontVariant)
                }
            }
            Result.Success(Unit)
        } catch (exception: Exception) {
            Result.Failure(Exception.FontResourceCreationException(exception))
        }

    }

}
