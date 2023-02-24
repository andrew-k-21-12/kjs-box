package io.github.andrewk2112.gradle.tasks

import io.github.andrewk2112.processors.IndependentResourceWrappersGenerator
import io.github.andrewk2112.resources.visitors.IconResourceVisitor
import io.github.andrewk2112.templates.wrappers.independent.IconIndependentWrappersWriter
import org.gradle.api.tasks.TaskAction

/**
 * Generates wrappers for source icon resources.
 */
abstract class IconWrappersGenerationTask : WrappersGenerationTask() {

    @TaskAction
    @Throws(Exception::class)
    private operator fun invoke() = inputsOutputs.run {
        IndependentResourceWrappersGenerator(
            IconResourceVisitor(),
            IconIndependentWrappersWriter()
        ).invoke(
            targetResourcesDirectory,
            subPathToBundledResources,
            wrappersOutDirectory,
            wrappersBasePackageName
        )
    }

}
