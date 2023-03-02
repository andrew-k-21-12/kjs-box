package io.github.andrewk2112.resources.visitors

import io.github.andrewk2112.models.LocalizationResource
import io.github.andrewk2112.utility.CollectingVisitor
import io.github.andrewk2112.utility.Result
import org.apache.commons.io.FilenameUtils
import org.jetbrains.kotlin.com.google.gson.JsonElement
import org.jetbrains.kotlin.com.google.gson.JsonParser
import java.io.File
import java.nio.file.Path
import kotlin.jvm.Throws

/**
 * A [CollectingVisitor] to gather all [LocalizationResource] metadata.
 *
 * @param targetResourcesDirectory A directory with all localization resources to be processed.
 */
internal class LocalizationResourceVisitor @Throws(Exception.PathsPreparationException::class) constructor(
    targetResourcesDirectory: File
) : CollectingVisitor<File, LocalizationResource, LocalizationResourceVisitor.Exception>() {

    // Utility.

    /**
     * Any failure can happen during the processing of localization resources.
     */
    internal sealed class Exception(cause: Throwable) : kotlin.Exception(cause) {

        /**
         * When some failure has happened while parsing file paths.
         */
        internal class PathsPreparationException(cause: Throwable) : Exception(cause)

        /**
         * When a translations file being processed has some bad format impossible to be analyzed.
         */
        internal class InvalidFileFormatException(cause: Throwable) : Exception(cause)

    }



    // Implementation.

    override fun visit(element: File): Result<Unit, Exception> {

        // Preparing file-related paths and names.
        val name = element.nameWithoutExtension
        val relativePath: String
        val locale: String
        try {
            val subPath  = targetResourcesDirectory.relativize(element.toPath())
            relativePath = FilenameUtils.getPathNoEndSeparator(subPath.subpath(1, subPath.nameCount).toString())
            locale       = subPath.first().toString()
        } catch (exception: kotlin.Exception) {
            return Result.Failure(Exception.PathsPreparationException(exception))
        }

        // Collecting all required data from the translations file.
        try {
            JsonParser.parseString(element.readText())
                      .collectKeys(name, relativePath, "", locale)
        } catch (e: kotlin.Exception) {
            return Result.Failure(Exception.InvalidFileFormatException(e))
        }

        return Result.Success(Unit)

    }



    // Private.

    /**
     * Collects all translation keys from the current [JsonElement] recursively.
     */
    @Throws(IllegalStateException::class)
    private fun JsonElement.collectKeys(name: String, relativePath: String, currentKey: String, locale: String) {

        // A leaf node (ending key) is reached.
        if (isJsonPrimitive) {
            addOrReplace({ it.name == name && it.relativePath == relativePath && it.fullKey == currentKey }) {
                if (it == null) { // meeting a translation key for the first time
                    LocalizationResource(name, relativePath, currentKey, setOf(locale))
                } else {          // adding one more locale for the key was already encountered
                    LocalizationResource(name, relativePath, currentKey, it.supportedLocales + locale)
                }
            }
            return
        }

        // A nested JSON object is encountered - going further.
        val jsonObject = asJsonObject
        for (key in jsonObject.keySet()) {
            jsonObject.get(key)
                      .collectKeys(name, relativePath, if (currentKey.isEmpty()) key else "$currentKey.$key", locale)
        }

    }

    /** A conversion to [Path] for convenience and optimization. */
    private val targetResourcesDirectory: Path = targetResourcesDirectory.toPath()

}
