package io.github.andrewk2112.gradle.tasks

import io.github.andrewk2112.extensions.createSymbolicLinkTo
import io.github.andrewk2112.extensions.joinWithPath
import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.specs.Spec
import org.gradle.api.tasks.*
import java.io.File
import kotlin.jvm.Throws

/**
 * Inputs and outputs for a family of wrappers generation tasks,
 * does the only action to prepare a symbolic link to the [targetResourcesDirectory]
 * to make the original resource files inside it accessible from the [resourcesOutDirectory].
 */
abstract class WrappersGenerationTask : DefaultTask() {

    // Utility.

    /**
     * All inputs and outputs of the [WrappersGenerationTask] gathered with convenient types.
     */
    open class InputsOutputs(
        val targetResourcesDirectory: File,
        val wrappersBasePackageName: String,
        val subPathToBundledResources: String,
        val wrappersOutDirectory: File,
    )



    // Public.

    init {
        // While it's recommended to do such configurations at the plugin level,
        // in this particular case such approach harms the encapsulation
        // and makes internal logic delegated to multiple places:
        // the check below works like a preliminary validation for the required input, it's better to be done in place.
        onlyIf {
            targetResourcesDirectory.asFile.orNull?.isDirectory == true
        }
    }

    // Making this method final to prevent the construction above from leaking.
    final override fun onlyIf(spec: Spec<in Task>) = super.onlyIf(spec)

    /** The directory with all target resources to be processed. */
    @get:InputDirectory
    abstract val targetResourcesDirectory: RegularFileProperty

    /** A base package name for all generated wrappers. */
    @get:Input
    abstract val wrappersBasePackageName: Property<String>

    /** A part of the relative path where the target resources are going to be stored inside the prepared bundle. */
    @get:Input
    abstract val subPathToBundledResources: Property<String>

    /** Where to write generated wrappers. */
    @get:OutputDirectory
    abstract val wrappersOutDirectory: RegularFileProperty

    /** A directory to prepare the files structure for accessing the original resource files by. */
    @get:OutputDirectory
    abstract val resourcesOutDirectory: RegularFileProperty



    // Protected.

    /** All inputs and outputs of the task in a convenient format. */
    @get:Internal
    @get:Throws(NullPointerException::class, IllegalStateException::class)
    protected val inputsOutputs: InputsOutputs by lazy {
        InputsOutputs(
            targetResourcesDirectory.asFile.get(),
            wrappersBasePackageName.get(),
            subPathToBundledResources.get(),
            wrappersOutDirectory.asFile.get(),
        )
    }



    // Private.

    /**
     * Makes the path created by joining the [resourcesOutDirectory] with the [subPathToBundledResources]
     * point to the [targetResourcesDirectory].
     */
    @TaskAction
    @Throws(Exception::class)
    private fun createResourcesDirectorySymLink() = inputsOutputs.run {
        resourcesOutDirectory.asFile.get()
            .joinWithPath(subPathToBundledResources)
            .createSymbolicLinkTo(targetResourcesDirectory)
    }

}
