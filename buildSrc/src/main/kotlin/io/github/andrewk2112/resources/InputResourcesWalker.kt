package io.github.andrewk2112.resources

import io.github.andrewk2112.utility.CollectingVisitor
import org.apache.tools.ant.BuildException
import org.apache.tools.ant.taskdefs.condition.Os
import java.io.File
import java.nio.file.Path

/**
 * This is a kind of frontend for generating various resource wrapper classes.
 * The main purpose of this class is just to walk through each file in the target directory
 * and collect all required resource metadata by calling the corresponding visitor for each.
 */
internal class InputResourcesWalker {

    // API.

    /**
     * Walks through all files inside the [targetResourcesDirectory]
     * and applies the [resourceVisitor] to each of them with providing the [subPathToBundledResources] to it.
     *
     * @return All collected data about resources of the target type [T].
     */
    @Throws(PathsPreparationException::class, FilesWalkingException::class, ResourceVisitingException::class)
    internal fun <T> walk(
        targetResourcesDirectory: File,
        subPathToBundledResources: String,
        resourceVisitor: CollectingVisitor<ResourcePaths, T, ResourceVisitingException>,
    ): List<T> {

        // A reusable path for optimization.
        val targetResourcesPath: Path
        try {
            if (!targetResourcesDirectory.isDirectory) { // checking if we can process something at all
                throw IllegalArgumentException("Not a directory: ${targetResourcesDirectory.absolutePath}")
            }
            targetResourcesPath = targetResourcesDirectory.toPath()
        } catch (exception: Exception) {
            throw PathsPreparationException(exception)
        }

        // Launching the visitor for each file.
        try {
            targetResourcesDirectory
                .walk()
                .filter(createFilteringPredicate())
                .forEach {
                    resourceVisitor.visit(
                        ResourcePaths(it, targetResourcesPath, subPathToBundledResources)
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
    @Throws(BuildException::class)
    private fun createFilteringPredicate(): (File) -> Boolean =
        when {
            Os.isFamily(Os.FAMILY_MAC) -> { file -> file.isFile && file.name != ".DS_Store" }
            else                       -> File::isFile
        }

}
