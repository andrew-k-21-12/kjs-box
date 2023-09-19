package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks.actions

import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks.WrappersGenerationTask
import io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.utility.CollectingVisitor
import org.apache.tools.ant.BuildException
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Transformer
import java.io.File

/**
 * Walks through each file in the [WrappersGenerationTask.targetResourcesDirectory]
 * and collects required resource metadata by calling a [resourceVisitor] for each.
 *
 * @param transformer Maps each file to the input type accepted by the [resourceVisitor].
 */
internal class CollectResourcesMetadataAction<T, R, E : Exception>(
    private val task: WrappersGenerationTask,
    private val transformer: Transformer<T, File>,
    private val resourceVisitor: CollectingVisitor<T, R, E>,
) {

    // Utility.

    /**
     * When some error while walking through the resources file tree has occurred.
     */
    internal class FilesWalkingException(cause: Throwable) : Exception(cause)

    /**
     * When visiting a particular resource has failed.
     */
    internal class ResourceVisitingException(cause: Throwable) : Exception(cause)



    // API.

    /**
     * Collects and returns resources' metadata of the target type [R].
     */
    @Throws(FilesWalkingException::class, ResourceVisitingException::class)
    internal fun collectResourcesMetadata(): List<R> {
        var error: Exception? = null
        try {
            for (file in task.targetResourcesDirectory.walk().filter(createFilteringPredicate()))
                try {
                    resourceVisitor.visit(transformer.transform(file))
                } catch (exception: Exception) {
                    error = ResourceVisitingException(exception)
                    break
                }
        } catch (exception: Exception) {
            error = FilesWalkingException(exception)
        }
        error?.let { throw it }
        return resourceVisitor.consume()
    }



    // Private.

    /**
     * Creates a predicate to filter out only valid files.
     */
    @Throws(BuildException::class)
    private fun createFilteringPredicate(): (File) -> Boolean =
        when {
            Os.isFamily(Os.FAMILY_MAC) -> { file -> file.isFile && file.name != ".DS_Store" }
            else                       -> File::isFile
        }

}
