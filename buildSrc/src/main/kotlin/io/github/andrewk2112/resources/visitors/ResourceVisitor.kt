package io.github.andrewk2112.resources.visitors

import java.io.File
import java.nio.file.Path

/**
 * A common contract for all resource visitors.
 * The purpose of it is to provide basic required information about each target resource for its further processing.
 * */
internal interface ResourceVisitor {

    /**
     * Processes each resource file.
     *
     * @param rootResourcesPath   A [Path] to the directory containing all resources.
     * @param targetResourcesPath A [Path] to the directory containing only required resources to be processed.
     * @param resourceFile        A resource [File] to be processed.
     *
     * @throws Exception When any kind of error happens.
     *                   It's not possible to narrow this exception down,
     *                   or it will violate The Liskov substitution principle.
     * */
    @Throws(Exception::class)
    fun visit(rootResourcesPath: Path, targetResourcesPath: Path, resourceFile: File)

}
