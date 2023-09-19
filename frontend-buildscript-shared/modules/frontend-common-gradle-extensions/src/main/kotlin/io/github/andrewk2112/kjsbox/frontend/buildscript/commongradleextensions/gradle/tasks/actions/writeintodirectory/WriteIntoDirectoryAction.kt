package io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.gradle.tasks.actions.writeintodirectory

import io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.utility.Result
import java.io.File
import java.io.Serializable

/**
 * Writes anything into a target directory.
 *
 * @param E A type of error can happen during writing.
 */
interface WriteIntoDirectoryAction<E : Exception> : Serializable {

    /**
     * Performs the actual writing into a [directory] and returns the corresponding [Result].
     */
    fun writeIntoDirectory(directory: File): Result<Unit, E>

}
