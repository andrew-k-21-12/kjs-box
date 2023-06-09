package io.github.andrewk2112.kjsbox.frontend.gradle.tasks

import io.github.andrewk2112.kjsbox.frontend.wrappers.writers.ImageWrappersWriter
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

/**
 * Generates base interfaces to be used by all image wrappers produced by the [ImageWrappersGenerationTask].
 */
abstract class ImageInterfacesGenerationTask : DefaultTask() {

    // Public.

    /** A package name for all generated image interfaces. */
    @get:Input
    abstract val interfacesPackageName: Property<String>

    /** Where to write generated image interfaces. */
    @get:OutputDirectory
    abstract val interfacesOutDirectory: RegularFileProperty



    // Private.

    @TaskAction
    @Throws(Exception::class)
    private operator fun invoke() =
        ImageWrappersWriter().writeBaseInterfaces(interfacesOutDirectory.asFile.get(), interfacesPackageName.get())

}
