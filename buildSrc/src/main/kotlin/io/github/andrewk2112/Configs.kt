package io.github.andrewk2112

import org.gradle.api.Project
import java.io.File

/**
 * All buildscript-related configs to avoid hardcoding and duplication.
 * */
object Configs {

    /**
     * Initializes root project's configs - must be called on the root [Project] before any other configs.
     * */
    fun Project.initRootProjectConfigs() {
        imageWrappersBaseDir = File(buildDir, "generated/imageWrappers")
    }

    /** The directory to store all image wrappers in. */
    lateinit var imageWrappersBaseDir: File
        private set

    /** The path to store the image wrappers' base interfaces at (inside the [imageWrappersBaseDir]). */
    const val BASE_IMAGE_INTERFACES_PATH = "core"

    /** The package to be used for all image wrappers. */
    const val IMAGE_WRAPPERS_PACKAGE = "io.github.andrewk2112.resources.images"

    // Version constants.
    const val KOTLIN_VERSION         = "1.7.10"
    const val KOTLIN_WRAPPER_VERSION = "pre.366"
    const val REACT_VERSION          = "18.2.0"

}
