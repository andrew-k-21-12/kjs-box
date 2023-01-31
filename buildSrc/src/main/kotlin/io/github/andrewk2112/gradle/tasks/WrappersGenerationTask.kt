package io.github.andrewk2112.gradle.tasks

import io.github.andrewk2112.utility.changeMonitor
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import java.io.File
import kotlin.jvm.Throws

/**
 * Inputs and outputs for wrappers generation tasks.
 */
abstract class WrappersGenerationTask : DefaultTask() {

    // Utility.

    /**
     * All inputs and outputs of the [WrappersGenerationTask] gathered with convenient types.
     */
    open class InputsOutputs(
        val allResourcesDirectory: File,
        val targetResourcesDirectory: File,
        val wrappersBasePackageName: String,
        val wrappersOutDirectory: File
    )



    // Public.

    /** The directory with all resources - needed to construct paths to wrapped resources. */
    @get:Internal
    var allResourcesDirectory: File? by changeMonitor(::setupTargetResourcesDirectory)

    /**
     * A path to the directory with all target resources to be processed
     * starting (excluding) from the [allResourcesDirectory], path segments are separated by slashes.
     */
    @get:Internal
    var pathToTargetResourcesDirectory: String? by changeMonitor(::setupTargetResourcesDirectory)

    /** A base package name for all generated wrappers. */
    @get:Input
    abstract val wrappersBasePackageName: Property<String>

    /** Where to write generated wrappers. */
    @get:OutputDirectory
    abstract val wrappersOutDirectory: RegularFileProperty



    // Protected.

    /**
     * Prepares all inputs and outputs of the task in a convenient format.
     */
    @Internal
    @Throws(NullPointerException::class, IllegalStateException::class)
    protected open fun getInputsOutputs(): InputsOutputs =
        InputsOutputs(
            allResourcesDirectory!!,
            targetResourcesDirectory.asFile.get(),
            wrappersBasePackageName.get(),
            wrappersOutDirectory.asFile.get()
        )

    /** The directory with all target resources to be processed. */
    @get:InputDirectory
    protected abstract val targetResourcesDirectory: RegularFileProperty



    // Private.

    /**
     * Setups the [targetResourcesDirectory] according to its dependency inputs.
     */
    private fun setupTargetResourcesDirectory() {
        targetResourcesDirectory.set(
            File(allResourcesDirectory ?: return, pathToTargetResourcesDirectory ?: return)
        )
    }

}
