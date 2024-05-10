package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks

import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks.actions.CollectResourcesMetadataAction
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks.actions.FileToResourcePathsTransformer
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks.actions.CreateSymLinkToResourcesAction
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks.actions.GenerateResourceWrappersAction
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.resources.visitors.FontResourceVisitor
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.wrappers.writers.independent.FontIndependentWrappersWriter
import org.gradle.api.tasks.*

/**
 * Generates wrappers for source font resources.
 */
internal abstract class FontWrappersGenerationTask : WrappersGenerationTask() {

    @TaskAction
    @Throws(Exception::class)
    private operator fun invoke() {
        val subPathToBundledResources = CreateSymLinkToResourcesAction(this).createFromResourcesTypeAndModuleName()
        val fontResources = CollectResourcesMetadataAction(
            this,
            FileToResourcePathsTransformer(this, subPathToBundledResources),
            FontResourceVisitor()
        ).collectResourcesMetadata()
        GenerateResourceWrappersAction(this, FontIndependentWrappersWriter())
            .generateFromResourcesMetadata(fontResources)
    }

}
