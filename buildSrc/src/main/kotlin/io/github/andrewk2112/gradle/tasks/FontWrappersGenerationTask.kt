package io.github.andrewk2112.gradle.tasks

import io.github.andrewk2112.gradle.tasks.actions.CollectResourcesMetadataAction
import io.github.andrewk2112.gradle.tasks.actions.FileToResourcePathsTransformer
import io.github.andrewk2112.gradle.tasks.actions.CreateSymLinkToResourcesAction
import io.github.andrewk2112.gradle.tasks.actions.GenerateResourceWrappersAction
import io.github.andrewk2112.resources.visitors.FontResourceVisitor
import io.github.andrewk2112.wrappers.writers.independent.FontIndependentWrappersWriter
import org.gradle.api.tasks.*

/**
 * Generates wrappers for source font resources.
 */
abstract class FontWrappersGenerationTask : WrappersGenerationTask() {

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
