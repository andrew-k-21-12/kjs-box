package io.github.andrewk2112.kjsbox.frontend.buildscript.resources

import org.apache.commons.io.FilenameUtils
import java.io.File
import java.nio.file.InvalidPathException
import java.nio.file.Path

/**
 * Prepares and holds path values to process a resource.
 *
 * @param resourceFile              The resource [File] itself.
 * @param targetResourcesDirectory  A [Path] to a subdirectory containing all target resources (resources of a type).
 * @param subPathToBundledResources A part of the relative path to all target resource files inside the prepared bundle.
 */
internal class ResourcePaths(
    internal val resourceFile: File,
    private val targetResourcesDirectory: Path,
    private val subPathToBundledResources: String,
) {

    // API.

    /**
     * Prepares and stores the final relative path to the [resourceFile]
     * starting from the [subPathToBundledResources].
     */
    @get:Throws(IllegalArgumentException::class, InvalidPathException::class)
    internal val relativeResourcePath: String by lazy { "$subPathToBundledResources/$originalRelativeResourcePath" }

    /**
     * Prepares and holds a relative sub-path to the [resourceFile]
     * excluding the filename and the ending separator,
     * starting (excluding) from the [targetResourcesDirectory].
     */
    @get:Throws(IllegalArgumentException::class, InvalidPathException::class)
    internal val subPathToResource: String by lazy { FilenameUtils.getPathNoEndSeparator(originalRelativeResourcePath) }



    // Private.

    /** Stores a reusable relative path to the [resourceFile] without the [subPathToBundledResources]. */
    @get:Throws(IllegalArgumentException::class, InvalidPathException::class)
    private val originalRelativeResourcePath: String by lazy {
        targetResourcesDirectory.relativize(resourceFile.toPath()).toString()
    }

}
