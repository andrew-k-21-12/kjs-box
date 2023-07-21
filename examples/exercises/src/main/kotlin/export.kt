package io.github.andrewk2112.kjsbox.frontend.gradle.tasks

import io.github.andrewk2112.kjsbox.frontend.extensions.joinCapitalized
import io.github.andrewk2112.kjsbox.frontend.extensions.joinWithPath
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.intellij.lang.annotations.Language

/**
 * Generates Kotlin sources to declare a component which is going to be exported as an entry point for a lazy module.
 */
internal abstract class LazyExportConfigGenerationTask : DefaultTask() {

    // Configurations.

    /** The target module's name in its original format. */
    @get:Input
    internal abstract val moduleName: Property<String>

    /** The name of component to be exported as an entry point. */
    @get:Input
    internal abstract val exportedComponentName: Property<String>

    /** Where to write the generated sources. */
    @get:OutputDirectory
    internal abstract val sourcesOutDirectory: RegularFileProperty



    // Action.

    @TaskAction
    @Throws(Exception::class)
    private fun generateAndWriteExportConfig() = sourcesOutDirectory.get().asFile
                                                                    .joinWithPath(exportFileName)
                                                                    .writeText(generateExportConfigCode())



    // Configs.

    /**
     * Generates Kotlin code to declare a component to be exported.
     */
    @Language("kotlin")
    @Throws(IllegalStateException::class)
    private fun generateExportConfigCode() = """
import react.VFC

/**
 * For lazy loading of components they should be exported as default.
 * Export will not work with any visibility modifier excepting public.
 * It's not possible to name the exported variable as "default" as it leads to the clash of public names.
 * This boilerplate is needed to remove the package from the component being exported
 * without harming its internal imports.
 */
@JsExport
@OptIn(ExperimentalJsExport::class)
@JsName("default")
@Suppress("NON_CONSUMABLE_EXPORTED_IDENTIFIER") // to avoid pointless warnings in the console which are not true
val ${formattedModuleName}: VFC = ${exportedComponentName.get()}

    """.trimIndent()

    /** Formats the original [moduleName] properly as a Kotlin variable. */
    @get:Throws(IllegalStateException::class)
    private inline val formattedModuleName: String get() = moduleName.get().split("-")
                                                                           .joinCapitalized()
                                                                           .replaceFirstChar { it.lowercaseChar() }

    /** This filename will be used as a destination to write component export config into. */
    private inline val exportFileName get() = "export.kt"

}