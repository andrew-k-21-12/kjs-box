package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks

import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks.actions.CollectResourcesMetadataAction
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks.actions.CreateSymLinkToResourcesAction
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks.actions.FileToResourcePathsTransformer
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks.actions.GenerateResourceWrappersAction
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.resources.visitors.IconResourceVisitor
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.wrappers.writers.independent.IconIndependentWrappersWriter

/**
 * Generates wrappers for source icon resources.
 */
internal abstract class IconWrappersGenerationTask : WrappersGenerationTask() {

    @Throws(Exception::class)
    override fun action() {
        super.action()
        val subPathToBundledResources = CreateSymLinkToResourcesAction(this).createFromResourcesTypeAndModuleName()
        val iconResources = CollectResourcesMetadataAction(
            this,
            FileToResourcePathsTransformer(this, subPathToBundledResources),
            IconResourceVisitor()
        ).collectResourcesMetadata()
        GenerateResourceWrappersAction(this, IconIndependentWrappersWriter())
            .generateFromResourcesMetadata(iconResources)
    }

}
