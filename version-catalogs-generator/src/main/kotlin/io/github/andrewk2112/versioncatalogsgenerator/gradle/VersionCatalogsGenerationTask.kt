package io.github.andrewk2112.versioncatalogsgenerator.gradle

import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.TypesCodeGenerator
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.VersionCatalogCodeGenerator
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.values.LibraryValuesCodeGenerator
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.values.PluginValuesCodeGenerator
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.values.SharedValuesCodeGeneration
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.values.VersionValuesCodeGenerator
import io.github.andrewk2112.versioncatalogsgenerator.extensions.dotsToSlashes
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
        val packageName             = packageName.get()
        val outDirectoryWithPackage = createOutDirectoryWithPackage(packageName)

        // Preparing all code generators and parsers to be used.
        val valuesCodeGeneration   = SharedValuesCodeGeneration()
        val librariesCodeGenerator = LibraryValuesCodeGenerator(valuesCodeGeneration)
        val pluginsCodeGenerator   = PluginValuesCodeGenerator(valuesCodeGeneration)
        val typesCodeGenerator     = TypesCodeGenerator(librariesCodeGenerator, pluginsCodeGenerator)
        val catalogParser          = TomlVersionCatalogParser()
        val catalogCodeGenerator   = VersionCatalogCodeGenerator(
                                         VersionValuesCodeGenerator(valuesCodeGeneration),
                                         librariesCodeGenerator,
                                         pluginsCodeGenerator
                                     )

        // Generating and writing the code.
        generateAndWriteTypesCode(typesCodeGenerator, packageName, outDirectoryWithPackage)
        generateAndWriteCatalogsCode(catalogParser, catalogCodeGenerator, packageName, outDirectoryWithPackage)

    }

    @Throws(Exception::class)
    private fun generateAndWriteTypesCode(
        typesCodeGenerator: TypesCodeGenerator,
        packageName: String,
        outDirectory: File
    ) {
        typesCodeGenerator.generate(packageName)
                          .also { File(outDirectory, "Types.kt").writeText(it) }
    }

    /**
     * Reads and parses each catalog from the provided [catalogs], generates and writes wrapper sources for them.
     */
    @Throws(Exception::class)
    private fun generateAndWriteCatalogsCode(
        catalogParser: TomlVersionCatalogParser,
        catalogCodeGenerator: VersionCatalogCodeGenerator,
        packageName: String,
        outDirectory: File
    ) =
        catalogs.get().forEach { catalog ->
            catalogParser.parseCatalog(catalog.path.get())
                        ?.let { catalogCodeGenerator.generate(packageName, catalog.name, it) }
                        ?.let { File(outDirectory, "${catalog.name}.kt").writeText(it) }
        }



    // Paths.

    @Throws(Exception::class)
    private fun createOutDirectoryWithPackage(packageName: String): File =
        File(sourcesOutDirectory.get().asFile, packageName.dotsToSlashes())
            .also { it.mkdirs() }

}
