package io.github.andrewk2112.kjsbox.frontend.buildscript.lazymoduleaccessors

import io.github.andrewk2112.utility.common.extensions.joinWithPath
import io.github.andrewk2112.utility.string.formats.cases.CamelCase
import io.github.andrewk2112.utility.string.formats.cases.KebabCase
import io.github.andrewk2112.utility.string.formats.cases.LowerCamelCase
import io.github.andrewk2112.utility.string.formats.changeFormat
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.intellij.lang.annotations.Language

/**
 * Performs generation of entry point component for a lazy (on-demand) module.
 *
 * Doesn't make any checks whether the requested generation conforms to some real lazy component.
 */
internal abstract class LazyEntryComponentGenerationTask : DefaultTask() {

    // Configurations.

    /** The name of root project to provide a kind of namespace. */
    @get:Input
    internal abstract val rootProjectName: Property<String>

    /** The module name of target project in its original format. */
    @get:Input
    internal abstract val moduleName: Property<String>

    /** The location of generated sources. */
    @get:OutputDirectory
    internal abstract val sourcesOutDirectory: RegularFileProperty



    // Action.

    @TaskAction
    @Throws(Exception::class)
    private fun generateAndWriteEntryComponent() {
        generatedComponentName.let {
            sourcesOutDirectory.get().asFile
                               .joinWithPath(generateEntryComponentFileName(it))
                               .writeText(generateEntryComponentCode(it))
        }
    }



    // Code generation.

    private fun generateEntryComponentFileName(generatedComponentName: String) = "$generatedComponentName.kt"

    @Language("kotlin")
    @Throws(IllegalStateException::class)
    private fun LazyEntryComponentGenerationTask.generateEntryComponentCode(generatedComponentName: String) =  """
import js.import.Module
import js.import.import
import js.promise.toPromise
import react.ExoticComponent
import react.Props
import react.lazy

internal val $generatedComponentName: ExoticComponent<Props> = lazy {
    // Such references (also, references to resources - fonts, icons, images)
    // are included from the compilation root (from the root resources directory):
    // their paths are totally unrelated to browser locations.
    // Therefore, we should use simple relative paths instead of the absolute ones.
    // It's a webpack requirement for requests that should resolve in the current directory to start with "./".
    import<Module<dynamic>>("./${rootProjectName.get()}-${moduleName.get()}")
        .then { it.default }
        .toPromise()
}

    """.trimIndent()

    /** Prepares a component name used as an entry point one. */
    @get:Throws(IllegalStateException::class)
    private inline val LazyEntryComponentGenerationTask.generatedComponentName: String
        get() = rootProjectName.get().changeFormat(KebabCase, LowerCamelCase) +
                moduleName.get().changeFormat(KebabCase, CamelCase) +
                "EntryPoint"

}
