package io.github.andrewk2112.resources

import io.github.andrewk2112.utility.CollectingVisitor
import org.apache.tools.ant.taskdefs.condition.Os
import java.io.File
import java.nio.file.Path

/**
 * This is a kind of frontend for generating various resource wrapper classes.
 * The main purpose of it is just to walk through each file in the target directory
 * and call the required visitor for it.
 */
internal class InputResourcesWalker {

    // API.

    /**
     * Walks through all files inside the [targetResourcesDir] and applies the [resourceVisitor] for each of them.
     *
     * @return All collected data about resources of the target type [T].
     */
    @Throws(PathsPreparationException::class, FilesWalkingException::class, ResourceVisitingException::class)
    internal fun <T> walk(
        rootResourcesDir: File,
        targetResourcesDir: File,
        resourceVisitor: CollectingVisitor<ResourcePaths, T, ResourceVisitingException>,
    ): List<T> {

        // Reusable paths for optimization.
        val rootResourcesPath:   Path
        val targetResourcesPath: Path
        try {
            if (!targetResourcesDir.isDirectory) { // checking if we can process something at all
                throw IllegalArgumentException("Not a directory: ${targetResourcesDir.absolutePath}")
            }
            rootResourcesPath   = rootResourcesDir.toPath()
            targetResourcesPath = targetResourcesDir.toPath()
        } catch (exception: Exception) {
            throw PathsPreparationException(exception)
        }

        // Launching the visitor for each file.
        try {
            targetResourcesDir
                .walk()
                .filter(createFilteringPredicate())
                .forEach {
                    resourceVisitor.visit(
                        ResourcePaths(it, rootResourcesPath, targetResourcesPath)
                    ).getThrowing()
                }
        } catch (exception: ResourceVisitingException) {
            throw exception
        } catch (exception: Exception) {
            throw FilesWalkingException(exception)
        }

        // Retrieving all the gathered resources' data.
        return resourceVisitor.consume()

    }



    // Private.

    /**
     * Creates the predicate to filter out only valid files.
     */
    @Throws(SecurityException::class)
    private fun createFilteringPredicate(): (File) -> Boolean =
        when {
            Os.isFamily(Os.FAMILY_MAC) -> { file -> file.isFile && file.name != ".DS_Store" }
            else                       -> File::isFile
        }

}
