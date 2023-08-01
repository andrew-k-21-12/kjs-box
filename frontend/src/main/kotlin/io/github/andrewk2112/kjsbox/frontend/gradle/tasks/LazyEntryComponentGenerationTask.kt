package io.github.andrewk2112.kjsbox.frontend.gradle.tasks

import io.github.andrewk2112.kjsbox.frontend.extensions.joinCapitalized
import io.github.andrewk2112.kjsbox.frontend.extensions.joinWithPath
import org.gradle.api.DefaultTask
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Project
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.intellij.lang.annotations.Language
import java.io.File



// Convenience usage.

/**
 * Registers a task to generate an entry point for an on-demand component (module)
 * and returns a [RegularFileProperty] pointing to the task's outputs.
 */
@Throws(IllegalStateException::class, InvalidUserDataException::class)
fun Project.generateLazyEntryComponent(): RegularFileProperty =
    tasks.register("generateLazyEntryComponent", LazyEntryComponentGenerationTask::class.java) {
        it.rootProjectName.set(rootProject.name)
        it.moduleName.set(name)
        it.sourcesOutDirectory.set(lazyEntryComponentDirectory)
    }.get().sourcesOutDirectory



// The task itself.

/**
 * Performs generation of entry point component for a lazy (on-demand) module.
 * Doesn't make any checks whether the requested generation conforms to some real lazy component.
 */
internal abstract class LazyEntryComponentGenerationTask : DefaultTask() {

    // Configurations.

    /** The name of root project to provide a kind of namespace. */
    @get:Input
    internal abstract val rootProjectName: Property<String>

    /** The module name of target project in its untouched format. */
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

}



// Configurations.

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
    // Extract string-formatting functions later.
    get() = rootProjectName.get().split("-").joinCapitalized().replaceFirstChar { it.lowercaseChar() } +
            moduleName.get().split("-").joinCapitalized() +
            "EntryPoint"

/** Where to write output entry component sources. */
private inline val Project.lazyEntryComponentDirectory: File
    get() = buildDir.joinWithPath("generated/entry")
