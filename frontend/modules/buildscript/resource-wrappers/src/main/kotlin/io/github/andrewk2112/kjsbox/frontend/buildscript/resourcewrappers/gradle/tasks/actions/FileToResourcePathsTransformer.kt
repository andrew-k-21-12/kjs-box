package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks.actions

import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks.WrappersGenerationTask
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.resources.ResourcePaths
import org.gradle.api.Transformer
import java.io.File
import java.nio.file.InvalidPathException
import java.nio.file.Path
import kotlin.jvm.Throws

/**
 * Transforms [File]s to [ResourcePaths].
 */
internal class FileToResourcePathsTransformer(
    task: WrappersGenerationTask,
    private val subPathToBundledResources: String
) : Transformer<ResourcePaths, File> {

    @Throws(InvalidPathException::class)
    override fun transform(`in`: File) = ResourcePaths(`in`, targetResourcesDirectory, subPathToBundledResources)

    /** Helps to reuse a [Path] to the [WrappersGenerationTask.targetResourcesDirectory]. */
    @get:Throws(InvalidPathException::class)
    private val targetResourcesDirectory: Path by lazy { task.targetResourcesDirectory.toPath() }

}
