package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.models

/**
 * Any resource having a [relativePath] provided.
 */
internal interface HavingRelativePath {

    /**
     * A short sub-path starting (excluding) from the directory with all resources of the required type
     * and ending (excluding) at the particular resource file.
     */
    val relativePath: String

}
