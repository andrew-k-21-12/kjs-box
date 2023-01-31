package io.github.andrewk2112.gradle.tasks

import io.github.andrewk2112.processors.IndependentResourceWrappersGenerator
import io.github.andrewk2112.resources.visitors.FontResourceVisitor
import io.github.andrewk2112.templates.wrappers.independent.FontIndependentWrappersWriter
import org.gradle.api.tasks.*

/**
 * Generates wrappers for source font resources.
 */
abstract class FontWrappersGenerationTask : WrappersGenerationTask() {

    @TaskAction
    @Throws(Exception::class)
    private operator fun invoke() {
        getInputsOutputs().apply {
            IndependentResourceWrappersGenerator(
                FontResourceVisitor(),
                FontIndependentWrappersWriter()
            ).invoke(
                allResourcesDirectory, targetResourcesDirectory, wrappersOutDirectory, wrappersBasePackageName
            )
        }
    }

}
