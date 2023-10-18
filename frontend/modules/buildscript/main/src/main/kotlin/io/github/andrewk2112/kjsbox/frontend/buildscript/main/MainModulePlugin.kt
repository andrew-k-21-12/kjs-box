package io.github.andrewk2112.kjsbox.frontend.buildscript.main

import io.github.andrewk2112.utility.common.extensions.joinWithPath
import io.github.andrewk2112.utility.common.utility.LazyReadOnlyProperty
import io.github.andrewk2112.utility.gradle.extensions.applyMultiplatform
import io.github.andrewk2112.utility.gradle.extensions.findTask
import io.github.andrewk2112.kjsbox.frontend.buildscript.shared.gradle.tasks.DirectoryWritingTask
import io.github.andrewk2112.kjsbox.frontend.buildscript.shared.gradle.tasks.actions.writeintodirectory.ResourceWriteIntoDirectoryAction
import io.github.andrewk2112.utility.common.utility.FromTo
import io.github.andrewk2112.kjsbox.frontend.buildscript.versioncatalogs.JsVersionCatalog
import io.github.andrewk2112.utility.gradle.extensions.jsMain
import io.github.andrewk2112.utility.gradle.extensions.registerTask
import org.gradle.api.Action
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

/**
 * The main plugin to set up the entire environment:
 * must be applied to the central Gradle module having no own sources but containing child modules of particular types.
 */
internal class MainModulePlugin : Plugin<Project> {

    // Action.

    @Throws(Exception::class)
    override fun apply(target: Project): Unit = target.run {

        // Generating Node.js binaries required for production.
        plugins.apply(NodeJsBinariesGenerationPlugin::class.java)

        // Making sure all supporting files to be prepared before they are actually needed.
        val unpackMainKt         by registerResourcesUnpackingTask(sourcesWithUnpackDestination)
        val unpackIndexHtml      by registerResourcesUnpackingTask(resourcesWithUnpackDestination)
        val unpackWebpackConfigs by registerResourcesUnpackingTask(webpackConfigsWithUnpackDestination)
        afterEvaluate { // making sure webpack configs are prepared before the compilation
            findTask("jsProcessResources").dependsOn(unpackWebpackConfigs)
        }

        applyMultiplatform {
            js {
                binaries.executable() // no run or deploy tasks will be generated without this line
                browser {
                    commonWebpackConfig(
                        Action { it.configDirectory = unpackWebpackConfigs.outDirectory.asFile.get() }
                    )
                }
            }
            sourceSets.jsMain {
                kotlin.srcDir(unpackMainKt)       // entry point sources
                resources.srcDir(unpackIndexHtml) // entry point HTML index
                dependencies {
                    // All required compile-only NPM dependencies.
                    JsVersionCatalog().bundles.kjsboxFrontendMain.forEach {
                        implementation(devNpm(it.name, it.version!!))
                    }
                }
            }
        }

        // Preventing optimization issues because of the custom modules structure.
        findTask("jsBrowserDevelopmentRun").dependsOn(findTask("jsDevelopmentExecutableCompileSync"))
        findTask("jsBrowserProductionRun").dependsOn(findTask("jsProductionExecutableCompileSync"))

    }



    // Private.

    /**
     * Registers a task to unpack built-in resources according to the provided [targets].
     */
    @Throws(IllegalStateException::class, InvalidUserDataException::class)
    private fun Project.registerResourcesUnpackingTask(
        targets: FromTo<Array<String>, File>
    ): LazyReadOnlyProperty<Any?, DirectoryWritingTask> =
        registerTask {
            writeActions.set(
                targets.source.map { ResourceWriteIntoDirectoryAction(it) }
                              .toTypedArray()
            )
            outDirectory.set(targets.destination)
        }



    // Configs.

    /**
     * A [File] pointing to a supporting files destination directory with a [directoryName].
     */
    private fun Project.getSupportingFilesDestinationDirectory(directoryName: String): File =
        layout.buildDirectory.asFile.get().joinWithPath("supportingFiles/$directoryName")

    /** States which sources are going to be unpacked and where. */
    private inline val Project.sourcesWithUnpackDestination: FromTo<Array<String>, File>
        get() = FromTo(
                    arrayOf("main.kt"),
                    getSupportingFilesDestinationDirectory("sources"),
                )

    /** States which resources are going to be unpacked and where. */
    private inline val Project.resourcesWithUnpackDestination: FromTo<Array<String>, File>
        get() = FromTo(
                    arrayOf("index.html"),
                    getSupportingFilesDestinationDirectory("resources"),
                )

    /** States which webpack config resources are going to be unpacked and where. */
    private inline val Project.webpackConfigsWithUnpackDestination: FromTo<Array<String>, File>
        get() = FromTo(
                    arrayOf(
                        "webpack/1-constants.js",
                        "webpack/2-shared.js",
                        "webpack/development.js",
                        "webpack/production.js",
                    ),
                    getSupportingFilesDestinationDirectory("webpackConfigs"),
                )

}
