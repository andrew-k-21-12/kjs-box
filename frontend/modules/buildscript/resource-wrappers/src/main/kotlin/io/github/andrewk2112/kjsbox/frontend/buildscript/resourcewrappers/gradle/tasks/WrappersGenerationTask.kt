package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks

import io.github.andrewk2112.utility.common.extensions.joinWithPath
import io.github.andrewk2112.utility.gradle.properties.RequiredDirectoryProperty
import io.github.andrewk2112.utility.common.utility.changeMonitor
import io.github.andrewk2112.utility.string.formats.changeFormat
import io.github.andrewk2112.utility.string.formats.other.PackageName
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import java.io.File

/**
 * Inputs and outputs for a family of wrappers generation tasks.
 */
internal abstract class WrappersGenerationTask : DefaultTask() {

    // All configurations required to be set.

    /** The root directory of resources - contains resource files of all kinds. */
    @get:Internal
    var allResourcesDirectory: File? by changeMonitor(::setupAllConfigurations)

    /** A package name common for the entire app - contains no module or other special identifying data. */
    @get:Internal
    var basePackageName: String? by changeMonitor(::setupAllConfigurations)

    /** The type of resources to generate wrappers for. */
    @get:Internal
    var resourcesTypeName: String? by changeMonitor(::setupAllConfigurations)

    /** The target module's name in its original format. */
    @get:Internal
    var moduleName: String? by changeMonitor(::setupAllConfigurations)

    /** A directory to put generated wrappers of all kinds into. */
    @get:Internal
    var generatedWrappersDir: File? by changeMonitor(::setupAllConfigurations)

    /** A directory to prepare the files structure for accessing the wrapped resource files by. */
    @get:OutputDirectory
    abstract val resourcesOutDirectory: RegularFileProperty



    // All derived configurations.

    /**
     * The directory with all target resources to be processed.
     *
     * Derived - not needed to be set manually.
     */
    @get:InputDirectory
    var targetResourcesDirectory: File by RequiredDirectoryProperty()

    /**
     * A base package name for all target generated wrappers.
     *
     * Derived - not needed to be set manually.
     */
    @get:Input
    abstract val wrappersBasePackageName: Property<String>

    /**
     * Where to write generated wrappers to.
     *
     * Derived - not needed to be set manually.
     */
    @get:OutputDirectory
    abstract val wrappersOutDirectory: RegularFileProperty

    /**
     * Setups all derived configurations from the required ones.
     */
    private fun setupAllConfigurations() {
        val resourcesTypeName = resourcesTypeName ?: return
        targetResourcesDirectory = allResourcesDirectory?.joinWithPath(resourcesTypeName) ?: return
        wrappersBasePackageName.set(
            (basePackageName ?: return) + "." +
            resourcesTypeName           + "." +
            (moduleName?.changeFormat(PackageName, PackageName) ?: return)
        )
        wrappersOutDirectory.set(generatedWrappersDir?.joinWithPath(resourcesTypeName) ?: return)
    }

}
