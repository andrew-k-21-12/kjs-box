package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks

import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks.actions.CreateSymLinkToResourcesAction
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks.actions.CollectResourcesMetadataAction
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks.actions.FileToResourcePathsTransformer
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.resources.visitors.ImageResourceVisitor
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.wrappers.writers.ImageWrappersWriter
import org.gradle.api.tasks.*

/**
 * Generates wrappers for source image resources.
 */
internal abstract class ImageWrappersGenerationTask : WrappersGenerationTask() {

    @Throws(Exception::class)
    override fun action() {
        super.action()

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
        val wrappersWriter          = ImageWrappersWriter()

        // Writing wrappers for image resources.
        for (imageResource in imageResources) {
            wrappersWriter.writeWrapper(
                wrappersOutDirectory,
                wrappersBasePackageName,
                imageResource
            )
        }

    }

}
