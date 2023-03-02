package io.github.andrewk2112.gradle.tasks

import io.github.andrewk2112.gradle.tasks.actions.CreateSymLinkToResourcesAction
import io.github.andrewk2112.gradle.tasks.actions.CollectResourcesMetadataAction
import io.github.andrewk2112.gradle.tasks.actions.FileToResourcePathsTransformer
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

        // Creating a symlink to image resources, collecting all image resources to generate wrappers for.
        val subPathToBundledResources = CreateSymLinkToResourcesAction(this).createFromResourcesTypeAndModuleName()
        val imageResources = CollectResourcesMetadataAction(
            this,
            FileToResourcePathsTransformer(this, subPathToBundledResources),
            ImageResourceVisitor()
        )
            .collectResourcesMetadata()
            .also { if (it.isEmpty()) return } // making sure there is something can be processed

        // Reusable.
        val wrappersOutDirectory    = wrappersOutDirectory.asFile.get()
        val wrappersBasePackageName = wrappersBasePackageName.get()
        val interfacesPackageName   = interfacesPackageName.get()
        val wrappersWriter          = ImageWrappersWriter()

        // Writing wrappers for image resources.
        for (imageResource in imageResources) {
            wrappersWriter.writeWrapper(
                wrappersOutDirectory,
                wrappersBasePackageName,
                interfacesPackageName,
                imageResource
            )
        }

    }

}
