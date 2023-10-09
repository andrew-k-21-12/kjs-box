package io.github.andrewk2112.kjsbox.frontend.buildscript.main

import io.github.andrewk2112.commonutility.extensions.joinWithPath
import io.github.andrewk2112.gradleutility.extensions.findTask
import io.github.andrewk2112.gradleutility.extensions.registerExecutionTask
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.*
import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsSetupTask
import java.io.File

/**
 * Generates all Node.js binaries required for production
 * (the ones not generated automatically for some reason).
 */
internal class NodeJsBinariesGenerationPlugin : Plugin<Project> {

    // Action.

    @Throws(Exception::class)
    override fun apply(target: Project) = target.run {
        afterEvaluate {

            // Reusable Node.js binary path and production compilation task (which depends on target libraries).
            val nodeJsBinary = findTask<NodeJsSetupTask>("kotlinNodeJsSetup").destination.joinWithPath(nodeJsBinaryPath)
            val productionCompilationTask = findTask("jsProductionExecutableCompileSync")

            targetLibraries.forEach { libraryName ->

                val libraryRootDirectory = getLibraryRootDirectory(libraryName)

                registerExecutionTask(
                    "generateNodeJs${libraryName.capitalized()}Binary",
                    nodeJsBinary,
                    libraryRootDirectory.joinWithPath(libraryInstallerPath)
                ).apply {
                    onlyIf { // no need to invoke the compilation if the binary already exists
                        !libraryRootDirectory.joinWithPath(getCompiledLibraryPath(libraryName)).isFile
                    }
                    dependsOn("kotlinNpmInstall") // making sure Node.js modules are fetched to perform the compilation
                    productionCompilationTask.dependsOn(this) // production bundling requires image libraries
                                                              // to perform minification for images
                }

            }

        }
    }



    // Private and configs.

    /** Node.js libraries to compile. */
    private inline val targetLibraries get() = arrayOf("cwebp", "optipng")

    /** Node.js binary subpath. */
    private inline val nodeJsBinaryPath: String
        get() = if (Os.isFamily(Os.FAMILY_WINDOWS)) "node" else "bin/node"

    /** Default location for a Node.js library installation script. */
    private inline val libraryInstallerPath get() = "lib/install.js"

    /**
     * Where a library is located (its root directory).
     */
    private fun Project.getLibraryRootDirectory(libraryName: String): File =
        layout.buildDirectory.asFile.get().joinWithPath("js/node_modules/$libraryName-bin")

    /**
     * Where Node.js outputs compiled binaries for libraries.
     */
    private fun getCompiledLibraryPath(libraryName: String) = "vendor/$libraryName"

}
