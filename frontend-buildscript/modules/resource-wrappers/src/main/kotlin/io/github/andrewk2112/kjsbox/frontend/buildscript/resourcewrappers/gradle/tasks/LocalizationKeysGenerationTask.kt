package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks

import io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.extensions.modifyIfNotEmpty
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks.actions.CollectResourcesMetadataAction
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks.actions.CreateSymLinkToResourcesAction
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.models.LocalizationResource
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.resources.visitors.LocalizationResourceVisitor
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.wrappers.writers.LocalizationWrappersWriter
import org.gradle.api.Transformer
import org.gradle.api.tasks.*
import java.io.File
import java.util.*
import kotlin.jvm.Throws

/**
 * Generates wrappers for localization keys.
 */
abstract class LocalizationKeysGenerationTask : WrappersGenerationTask() {

    /**
     * Describes a group of translations located within a single file.
     */
    private data class LocalizationFile(val relativePath: String, val name: String)

    @TaskAction
    @Throws(Exception::class)
    private operator fun invoke() {

        // Preparing the bundled structure of locales.
        CreateSymLinkToResourcesAction(this).createFromLocaleDirectories()

        // Gathering all localizations to prepare wrappers for.
        val localizationResources = CollectResourcesMetadataAction(
            this,
            Transformer<File, File> { it },
            LocalizationResourceVisitor(targetResourcesDirectory)
        )
            .collectResourcesMetadata()
            .apply { warnAboutMissingTranslations() }

        // Grouping all localizations by their original files.
        // Yes, this introduces one more pass - some optimization would be appreciated.
        val localizationFiles = localizationResources.groupBy { LocalizationFile(it.relativePath, it.name) }

        // Reusable.
        val wrappersOutDirectory    = wrappersOutDirectory.asFile.get()
        val wrappersBasePackageName = wrappersBasePackageName.get()
        val wrappersWriter          = LocalizationWrappersWriter()

        // Writing wrappers for localization keys.
        localizationFiles.forEach { (file, localization) ->
            wrappersWriter.writeLocalizationKeys(
                wrappersOutDirectory,
                moduleName!!, // should be safe as dependent variables must be already validated at this moment
                wrappersBasePackageName,
                file.relativePath,
                file.name,
                localization.map { it.fullKey }.toTypedArray()
            )
        }

    }

    /**
     * Adds console warnings for all [LocalizationResource]s having some translations not provided.
     *
     * It can be better to extract this method as a separate Gradle task.
     *
     * This and similar tasks are not optimal:
     * 1. For this particular task there are 3 passes -
     *    to collect all metadata, collect all provided locales and validate each translation:
     *    all this can be done by only 1 pass inside the corresponding visitor.
     * 2. For this particular task all JSONs are loaded entirely into memory -
     *    it's better to analyze them by using streams.
     * 3. For all tasks - all metadata is loaded entirely into memory:
     *    it can be harmful when there are lots of various resources to be processed.
     */
    private fun List<LocalizationResource>.warnAboutMissingTranslations() {
        val allLocales = distinctBy { it.supportedLocales }.flatMap { it.supportedLocales }
        for (localizationResource in this) {
            (allLocales - localizationResource.supportedLocales)
                .takeIf { it.isNotEmpty() }
               ?.let { missingLocales ->
                   logger.warn(
                       moduleName + "->" +
                       localizationResource.relativePath.modifyIfNotEmpty { "$it->" } +
                       localizationResource.name + "->" +
                       localizationResource.fullKey + " misses translations: ${missingLocales.joinToString()}"
                   )
               }
        }
    }

}
