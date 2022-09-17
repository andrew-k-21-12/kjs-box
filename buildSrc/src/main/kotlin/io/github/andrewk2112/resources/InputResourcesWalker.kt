package io.github.andrewk2112.resources

import io.github.andrewk2112.resources.visitors.ResourceVisitor
import java.io.File
import java.nio.file.InvalidPathException

/**
 * This is a kind of frontend for generating various resource wrapper classes.
 * The main purpose of it is just to walk through each file in the target directory
 * and call the required visitor for it.
 * */
internal class InputResourcesWalker {

    /**
     * Walks through all files inside the [targetResourcesDir] and applies the [resourceVisitor] for each of them.
     *
     * @throws Exception When any error happens.
     *                   It is represented by [IllegalArgumentException], [SecurityException], [InvalidPathException]
     *                   or any other exception thrown by the particular [resourceVisitor].
     * */
    @Throws(Exception::class)
    internal fun walk(rootResourcesDir: File, targetResourcesDir: File, resourceVisitor: ResourceVisitor) {

        // Checking if we can process something at all.
        if (!targetResourcesDir.isDirectory) {
            throw IllegalArgumentException("Not a directory: ${targetResourcesDir.absolutePath}")
        }

        // Launching the visitor for each file.
        targetResourcesDir
            .walk()
            .filter(File::isFile)
            .forEach { resourceVisitor.visit(rootResourcesDir.toPath(), targetResourcesDir.toPath(), it) }

    }

}
