package io.github.andrewk2112.gradle.tasks

import io.github.andrewk2112.resources.InputResourcesWalker
import io.github.andrewk2112.resources.visitors.ImageResourceVisitor
import io.github.andrewk2112.templates.wrappers.ImageWrappersWriter
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*

/**
 * Generates wrappers for source image resources.
 */
abstract class ImageWrappersGenerationTask : WrappersGenerationTask() {

    // Public.

    /** The package name of dependency image interfaces. */
    @get:Input
    abstract val interfacesPackageName: Property<String>



    // Private.

    @TaskAction
    @Throws(Exception::class)
    private operator fun invoke() {

        // Collecting all image resources to generate wrappers for, making sure there is something to be processed.
        val imageResources = InputResourcesWalker()
            .walk(
                inputsOutputs.targetResourcesDirectory,
                inputsOutputs.subPathToBundledResources,
                ImageResourceVisitor()
            )
            .also { if (it.isEmpty()) return }

        // Reusable.
        val interfacesPackageName = interfacesPackageName.get()
        val wrappersWriter        = ImageWrappersWriter()

        // Writing wrappers for image resources.
        for (imageResource in imageResources) {
            wrappersWriter.writeWrapper(
                inputsOutputs.wrappersOutDirectory,
                inputsOutputs.wrappersBasePackageName,
                interfacesPackageName,
                imageResource
            )
        }

    }

}
