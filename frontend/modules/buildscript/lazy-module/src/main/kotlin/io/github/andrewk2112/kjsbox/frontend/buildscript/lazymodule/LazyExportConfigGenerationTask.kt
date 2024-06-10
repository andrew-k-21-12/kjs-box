package io.github.andrewk2112.kjsbox.frontend.buildscript.lazymodule

import io.github.andrewk2112.utility.common.extensions.joinWithPath
import io.github.andrewk2112.utility.string.formats.cases.CamelCase
import io.github.andrewk2112.utility.string.formats.cases.KebabCase
import io.github.andrewk2112.utility.string.formats.changeFormat
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.intellij.lang.annotations.Language

/**
 * Generates Kotlin sources to mark a component to be exported as an entry point for a lazy module.
 */
internal abstract class LazyExportConfigGenerationTask : DefaultTask() {

    // Inputs and outputs configurations.

    /** The target module's name in its original format. */
    @get:Input
    internal abstract val moduleName: Property<String>

    /** The name of component (including full package) to be exported as an entry point. */
    @get:Input
    internal abstract val componentToExport: Property<String>

    /** Where to write the generated sources. */
    @get:OutputDirectory
    internal abstract val sourcesOutDirectory: RegularFileProperty



    // Action.

    @TaskAction
    @Throws(Exception::class)
    private fun generateAndWriteExportConfig() {
        generatedComponentName.let {
            sourcesOutDirectory.get().asFile
                               .joinWithPath(generateExportConfigFileName(it))
                               .writeText(generateExportConfigCode(it))
        }
    }



    // Configurations.

    /**
     * Generates a filename to be used as a destination to write component export config into.
     */
    private fun generateExportConfigFileName(generatedComponentName: String) = "$generatedComponentName.kt"

    /**
     * Generates Kotlin code to declare a component to be exported.
     */
    @Language("kotlin")
    @Throws(IllegalStateException::class)
    private fun generateExportConfigCode(generatedComponentName: String) = """
import react.FC
import react.Props

/**
 * For lazy loading of components they should be exported as default.
 * Export will not work with any visibility modifier excepting public.
 * It's not possible to name the exported variable as "default" as it leads to the clash of public names.
 * This boilerplate is needed to remove the package from the component being exported
 * without harming its internal imports 
* (otherwise when importing this module it will be needed to state its full package instead of just "default").
 */
@JsExport
@OptIn(ExperimentalJsExport::class)
@JsName("default")
@Suppress("NON_CONSUMABLE_EXPORTED_IDENTIFIER") // to avoid pointless warnings in the console which are not true
val $generatedComponentName: FC<Props> = ${componentToExport.get()}

    """.trimIndent()

    /** Prepares a component name used as an export point-configuring one. */
    @get:Throws(IllegalStateException::class)
    private inline val generatedComponentName: String
        get() = moduleName.get().changeFormat(KebabCase, CamelCase) + "ExportPoint"

}
