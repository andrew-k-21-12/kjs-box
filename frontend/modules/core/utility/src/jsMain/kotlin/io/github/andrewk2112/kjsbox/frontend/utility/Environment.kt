package io.github.andrewk2112.kjsbox.frontend.utility

import io.github.andrewk2112.kjsbox.frontend.utility.Environment.BuildMode.DEVELOPMENT

/**
 * Descriptions and configurations of the current environment.
 */
object Environment {

    // Utility.

    /**
     * A build mode the application can run in.
     */
    enum class BuildMode { DEVELOPMENT, PRODUCTION }



    // Public.

    /** Current [BuildMode] of the application. */
    val buildMode: BuildMode by lazy {
        // This "BUILD_MODE" comes from a webpack-defined variable,
        // see webpack config files from the corresponding buildscript Gradle module.
        val jsBuildMode = js("BUILD_MODE").unsafeCast<String>()
        BuildMode.entries.find {
            it.name.equals(jsBuildMode, ignoreCase = true)
        } ?: DEVELOPMENT
    }

}
