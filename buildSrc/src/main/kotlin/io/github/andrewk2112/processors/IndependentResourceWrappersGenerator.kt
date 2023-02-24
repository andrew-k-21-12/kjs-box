package io.github.andrewk2112.processors

import io.github.andrewk2112.models.HavingRelativePath
import io.github.andrewk2112.resources.FilesWalkingException
import io.github.andrewk2112.resources.InputResourcesWalker
import io.github.andrewk2112.resources.PathsPreparationException
import io.github.andrewk2112.resources.ResourcePaths
import io.github.andrewk2112.resources.ResourceVisitingException
import io.github.andrewk2112.templates.wrappers.WrapperWritingException
import io.github.andrewk2112.templates.wrappers.independent.IndependentWrappersWriter
import io.github.andrewk2112.utility.CollectingVisitor
import java.io.File

/**
 * Generates resource wrappers without any dependencies.
 */
internal class IndependentResourceWrappersGenerator<T : HavingRelativePath>(
    private val resourcesVisitor: CollectingVisitor<ResourcePaths, T, ResourceVisitingException>,
    private val wrappersWriter: IndependentWrappersWriter<T>,
    private val inputResourcesWalker: InputResourcesWalker = InputResourcesWalker()
) {

    /**
     * Invokes the wrappers' generation.
     *
     * @param targetResourcesDirectory  The directory with all resources of the target type [T]
     *                                  (to prepare relative packages).
     * @param subPathToBundledResources A part of the relative path
     *                                  where the target resources are going to be stored inside the prepared bundle.
     * @param wrappersOutDirectory      Where to write generated wrappers.
     * @param wrappersBasePackageName   A base package name for all generated wrappers.
     */
    @Throws(
        PathsPreparationException::class,
        FilesWalkingException::class,
        ResourceVisitingException::class,
        WrapperWritingException::class
    )
    internal operator fun invoke(
        targetResourcesDirectory: File,
        subPathToBundledResources: String,
        wrappersOutDirectory: File,
        wrappersBasePackageName: String
    ) {

        // Collecting the metadata about all required resources and checking whether there is something to be processed.
        val resources = inputResourcesWalker.walk(targetResourcesDirectory, subPathToBundledResources, resourcesVisitor)
                                            .also { if (it.isEmpty()) return }

        // Writing wrappers for all visited resources.
        for (resource in resources) {
            wrappersWriter.writeWrapper(wrappersOutDirectory, wrappersBasePackageName, resource)
        }

    }

}
