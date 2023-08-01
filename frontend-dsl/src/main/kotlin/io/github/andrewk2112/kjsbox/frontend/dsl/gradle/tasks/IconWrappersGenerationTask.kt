package io.github.andrewk2112.kjsbox.frontend.dsl.gradle.tasks

import io.github.andrewk2112.kjsbox.frontend.dsl.gradle.tasks.actions.CollectResourcesMetadataAction
import io.github.andrewk2112.kjsbox.frontend.dsl.gradle.tasks.actions.CreateSymLinkToResourcesAction
import io.github.andrewk2112.kjsbox.frontend.dsl.gradle.tasks.actions.FileToResourcePathsTransformer
import io.github.andrewk2112.kjsbox.frontend.dsl.gradle.tasks.actions.GenerateResourceWrappersAction
import io.github.andrewk2112.kjsbox.frontend.dsl.resources.visitors.IconResourceVisitor
import io.github.andrewk2112.kjsbox.frontend.dsl.wrappers.writers.independent.IconIndependentWrappersWriter
import org.gradle.api.tasks.TaskAction

/**
 * Generates wrappers for source icon resources.
 */
abstract class IconWrappersGenerationTask : WrappersGenerationTask() {

    @TaskAction
    @Throws(Exception::class)
    private operator fun invoke() {
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
