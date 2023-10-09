package io.github.andrewk2112.versioncatalogsgenerator.gradle

import io.github.andrewk2112.commonutility.extensions.ensureDirectoryExistsOrThrow
import io.github.andrewk2112.commonutility.extensions.joinWithPath
import io.github.andrewk2112.commonutility.extensions.writeTo
import io.github.andrewk2112.stringutility.formats.PackageFormat
import io.github.andrewk2112.stringutility.formats.PathFormat
import io.github.andrewk2112.stringutility.formats.changeFormat
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.CommonCodeGeneration
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.TypesCodeGenerator
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.VersionCatalogCodeGenerator
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.values.BundleValuesCodeGenerator
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.values.LibraryValuesCodeGenerator
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.values.PluginValuesCodeGenerator
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.values.VersionValuesCodeGenerator
import io.github.andrewk2112.versioncatalogsgenerator.parsers.TomlVersionCatalogParser
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * Generates and writes Kotlin wrapper sources for TOML version catalogs.
 */
internal abstract class VersionCatalogsGenerationTask : DefaultTask() {

    // Configurations.

    /** A package name to be used for all generated sources. */
    @get:Input
    internal abstract val packageName: Property<String>

    /** A [VisibilityModifier] to generate all sources with. */
    @get:Input
    internal abstract val visibilityModifier: Property<VisibilityModifier>

    /** All catalogs to generate wrappers for. */
    @get:Input
    internal abstract val catalogs: ListProperty<VersionCatalog>

    /** A destination directory to write generated wrappers into. */
    @get:OutputDirectory
    internal abstract val sourcesOutDirectory: RegularFileProperty



    // Action.

    @TaskAction
    @Throws(Exception::class)
    private operator fun invoke() {

        // Preparing values of reusable configs.
        val packageName              = packageName.get()
        val visibilityModifierPrefix = generateVisibilityModifierPrefix()
        val outDirectoryWithPackage  = createOutDirectoryWithPackage(packageName)

        // Preparing all code generators and parsers to be used.
        val commonCodeGeneration   = CommonCodeGeneration()
        val librariesCodeGenerator = LibraryValuesCodeGenerator(commonCodeGeneration)
        val pluginsCodeGenerator   = PluginValuesCodeGenerator(commonCodeGeneration)
        val typesCodeGenerator     = TypesCodeGenerator(librariesCodeGenerator, pluginsCodeGenerator)
        val catalogParser          = TomlVersionCatalogParser()
        val catalogCodeGenerator   = VersionCatalogCodeGenerator(
                                         VersionValuesCodeGenerator(commonCodeGeneration),
                                         librariesCodeGenerator,
                                         BundleValuesCodeGenerator(commonCodeGeneration),
                                         pluginsCodeGenerator
                                     )

        // Generating and writing the code.
        generateAndWriteTypesCode(typesCodeGenerator, packageName, visibilityModifierPrefix, outDirectoryWithPackage)
        generateAndWriteCatalogsCode(
            catalogParser, catalogCodeGenerator, packageName, visibilityModifierPrefix, outDirectoryWithPackage
        )

    }

    @Throws(IllegalStateException::class)
    private fun generateVisibilityModifierPrefix(): String =
        when (visibilityModifier.get()) {
            VisibilityModifier.PUBLIC   -> ""
            VisibilityModifier.INTERNAL -> "internal "
            null                        -> throw IllegalStateException() // should not ever happen
        }

    @Throws(Exception::class)
    private fun generateAndWriteTypesCode(
        typesCodeGenerator: TypesCodeGenerator,
        packageName: String,
        visibilityModifierPrefix: String,
        outDirectory: File
    ) =
        typesCodeGenerator.generate(packageName, visibilityModifierPrefix)
                          .writeTo(
                              outDirectory.joinWithPath("Types.kt")
                          )

    /**
     * Reads and parses each catalog from the provided [catalogs], generates and writes wrapper sources for them.
     */
    @Throws(Exception::class)
    private fun generateAndWriteCatalogsCode(
        catalogParser: TomlVersionCatalogParser,
        catalogCodeGenerator: VersionCatalogCodeGenerator,
        packageName: String,
        visibilityModifierPrefix: String,
        outDirectory: File
    ) =
        catalogs.get().forEach { catalog ->
            catalogParser.parseCatalog(catalog.path.get())
                        ?.let { catalogCodeGenerator.generate(packageName, visibilityModifierPrefix, catalog.name, it) }
                        ?.writeTo(
                            outDirectory.joinWithPath("${catalog.name}.kt")
                        )
        }



    // Paths.

    @Throws(Exception::class)
    private fun createOutDirectoryWithPackage(packageName: String): File =
        sourcesOutDirectory.get().asFile.joinWithPath(
            packageName.changeFormat(PackageFormat, PathFormat)
        ).apply {
            ensureDirectoryExistsOrThrow("Can not create an output directory: $this")
        }

}
