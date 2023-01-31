package io.github.andrewk2112.resources

import org.apache.commons.io.FilenameUtils
import java.io.File
import java.nio.file.InvalidPathException
import java.nio.file.Path

/**
 * Computes and holds path values to process a resource.
 *
 * @param resourceFile             The resource [File] itself.
 * @param rootResourcesDirectory   The [Path] to the root resources directory.
 * @param targetResourcesDirectory The [Path] to a subdirectory containing all target resources (resources of a type).
 */
internal class ResourcePaths(
    internal val resourceFile: File,
    private val rootResourcesDirectory: Path,
    private val targetResourcesDirectory: Path,
) {

    // API.

    /**
     * Computes and stores a relative path to the [resourceFile]
     * starting (excluding) from the [rootResourcesDirectory].
     */
    @get:Throws(IllegalArgumentException::class, InvalidPathException::class, UnsupportedOperationException::class)
    internal val relativeResourcePath: String by lazy {
        rootResourcesDirectory.relativize(absoluteResourcePath).toString()
    }

    /**
     * Computes and stores a relative sub-path to the [resourceFile]
     * excluding the filename and the ending separator,
     * starting (excluding) from the [targetResourcesDirectory].
     */
    @get:Throws(IllegalArgumentException::class, InvalidPathException::class, UnsupportedOperationException::class)
    internal val subPathToResource: String by lazy {
        FilenameUtils.getPathNoEndSeparator(
            targetResourcesDirectory.relativize(absoluteResourcePath).toString()
        )
    }



    // Private.

    /** Stores a reusable absolute [Path] to the [resourceFile]. */
    @get:Throws(InvalidPathException::class)
    private val absoluteResourcePath: Path by lazy { resourceFile.toPath() }

}
