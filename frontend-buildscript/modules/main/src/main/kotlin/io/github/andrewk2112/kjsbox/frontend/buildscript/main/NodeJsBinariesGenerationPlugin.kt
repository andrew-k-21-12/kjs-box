package io.github.andrewk2112.kjsbox.frontend.buildscript.main

import io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.extensions.joinWithPath
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

    override fun apply(target: Project) = target.run {
        afterEvaluate {
            val nodeJsBinary = findNodeJsBinary()
            // Production builds require the image minification binaries, otherwise the image minification won't succeed.
            tasks.named("productionExecutableCompileSync").get()
                 .dependsOn(
                    registerNodeJsBinaryGenerationTask(nodeJsBinary, "cwebp"),
                    registerNodeJsBinaryGenerationTask(nodeJsBinary, "optipng")
                )
        }
    }



    // Private.

    /**
     * Looks for a Node.js binary [File].
     */
    @Throws(UnknownTaskException::class)
    private fun Project.findNodeJsBinary(): File =
        tasks
            .named("kotlinNodeJsSetup", NodeJsSetupTask::class.java)
            .get().destination
            .joinWithPath(if (Os.isFamily(Os.FAMILY_WINDOWS)) "node" else "bin/node")

    /**
     * Registers a task making Node.js generate a required binary manually.
     *
     * @param nodeJsBinary A location of the Node.js binary to be used.
     * @param libraryName  A Node.js library name to generate the binary for.
     *
     * @return A reference to the registered [Task].
     */
    @Throws(InvalidUserDataException::class, SecurityException::class)
    private fun Project.registerNodeJsBinaryGenerationTask(
        nodeJsBinary: File,
        libraryName: String
    ): Task = tasks.register("generateNodeJs${libraryName.capitalized()}Binary").get().apply {

        // Making sure Node.js modules are fetched.
        dependsOn("kotlinNpmInstall")

        // Preparing the root directory of the library to be reused.
        val libraryBaseDir = File(project.buildDir, "js/node_modules/$libraryName-bin")

        // No need to invoke the task with the corresponding compilation if the binary exists.
        onlyIf {
            !File(libraryBaseDir, "vendor/$libraryName").isFile
        }

        // Executing the compilation for the target library.
        doLast {
            exec {
                it.commandLine(nodeJsBinary, File(libraryBaseDir, "lib/install.js"))
            }
        }

    }

}
