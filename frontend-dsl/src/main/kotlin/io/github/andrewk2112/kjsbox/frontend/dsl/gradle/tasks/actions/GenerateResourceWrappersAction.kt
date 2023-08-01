package io.github.andrewk2112.kjsbox.frontend.dsl.gradle.tasks.actions

import io.github.andrewk2112.kjsbox.frontend.dsl.gradle.tasks.WrappersGenerationTask
import io.github.andrewk2112.kjsbox.frontend.dsl.models.HavingRelativePath
import io.github.andrewk2112.kjsbox.frontend.dsl.wrappers.writers.WrapperWritingException
import io.github.andrewk2112.kjsbox.frontend.dsl.wrappers.writers.independent.IndependentWrappersWriter

/**
 * Generates simple resource wrappers without any dependencies.
 */
internal class GenerateResourceWrappersAction<T : HavingRelativePath>(
    private val task: WrappersGenerationTask,
    private val wrappersWriter: IndependentWrappersWriter<T>,
) {

    /**
     * Generates and writes wrappers for the [resources].
     */
    @Throws(IllegalStateException::class, WrapperWritingException::class)
    internal fun generateFromResourcesMetadata(resources: List<T>) {

        // An optimization to avoid the pointless actions below.
        if (resources.isEmpty()) return

        // Reusable arguments.
        val wrappersOutDirectory    = task.wrappersOutDirectory.asFile.get()
        val wrappersBasePackageName = task.wrappersBasePackageName.get()

        // Writing wrappers for all resources.
        for (resource in resources) {
            wrappersWriter.writeWrapper(wrappersOutDirectory, wrappersBasePackageName, resource)
        }

    }

}
