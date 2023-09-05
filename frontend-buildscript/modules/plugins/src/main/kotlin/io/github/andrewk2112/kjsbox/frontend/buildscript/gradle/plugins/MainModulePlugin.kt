package io.github.andrewk2112.kjsbox.frontend.buildscript.gradle.plugins

import io.github.andrewk2112.kjsbox.frontend.buildscript.extensions.joinWithPath
import io.github.andrewk2112.kjsbox.frontend.buildscript.gradle.extensions.devNpm
import io.github.andrewk2112.kjsbox.frontend.buildscript.gradle.extensions.kotlinJs
import io.github.andrewk2112.kjsbox.frontend.buildscript.gradle.tasks.DirectoryWritingTask
import io.github.andrewk2112.kjsbox.frontend.buildscript.gradle.tasks.actions.writeintodirectory.ResourceWriteIntoDirectoryAction
import io.github.andrewk2112.kjsbox.frontend.buildscript.utility.FromTo
import io.github.andrewk2112.kjsbox.frontend.buildscript.utility.LazyReadOnlyProperty
import io.github.andrewk2112.kjsbox.frontend.buildscript.versions.JsVersionCatalog
import io.github.andrewk2112.kjsbox.frontend.buildscript.versions.Library
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper
import java.io.File

/**
 * The main plugin to set up the entire environment:
 * must be applied to the central Gradle module having no own sources but containing child modules of particular types.
 */
internal class MainModulePlugin : Plugin<Project> {

    // Action.

    @Throws(Exception::class)
    override fun apply(target: Project): Unit = target.run {

        with(plugins) {
            apply(KotlinJsPluginWrapper::class.java)
            apply(NodeJsBinariesGenerationPlugin::class.java) // generating Node.js binaries required for production
        }

        // Making sure all supporting files to be prepared before they are actually needed.
        val unpackMainKt    by registerResourcesUnpackingTask(sourcesWithUnpackDestination)
        val unpackIndexHtml by registerResourcesUnpackingTask(resourcesWithUnpackDestination)
        val webpackConfigsDirectory = registerWebpackConfigsUnpackingTask()

        kotlinJs {

            js {
                binaries.executable() // no run or deploy tasks will be generated without this line
                browser {
                    commonWebpackConfig {
                        configDirectory = webpackConfigsDirectory
                    }
                }
            }

            sourceSets.named("main").get().apply {
                kotlin.srcDir(unpackMainKt)       // entry point sources
                resources.srcDir(unpackIndexHtml) // entry point HTML index
            }

        }

        // Adding all required development-only NPM dependencies.
        devNpmDependencies.forEach {
            dependencies.add("implementation", dependencies.devNpm(it.name, it.version!!))
        }

        // Preventing optimization issues because of the custom modules structure.
        tasks.apply {
            named("browserDevelopmentRun").get().dependsOn(named("developmentExecutableCompileSync"))
            named("browserProductionRun").get().dependsOn(named("productionExecutableCompileSync"))
        }

    }



    // Private.

    /**
     * Registers a task to unpack built-in resources according to the provided [targets].
     */
    @Throws(IllegalStateException::class, InvalidUserDataException::class)
    private fun Project.registerResourcesUnpackingTask(
        targets: FromTo<Array<String>, File>
    ) = LazyReadOnlyProperty<Any?, DirectoryWritingTask> { property ->
        tasks.register(property.name, DirectoryWritingTask::class.java).get().apply {
            writeActions.set(
                targets.source.map { ResourceWriteIntoDirectoryAction(it) }
                              .toTypedArray()
            )
            outDirectory.set(targets.destination)
        }
    }

    /**
     * Registers a task to unpack all required webpack configs from built-in resources
     * according to the [webpackConfigsWithUnpackDestination]
     * and returns the corresponding destination directory.
     */
    @Throws(IllegalStateException::class, InvalidUserDataException::class)
    private fun Project.registerWebpackConfigsUnpackingTask(): File {
        val targets = webpackConfigsWithUnpackDestination
        val unpackWebpackConfigs by registerResourcesUnpackingTask(targets)
        afterEvaluate { // make sure the task is going to be executed during the build
            tasks.named("processResources").get().dependsOn(unpackWebpackConfigs)
        }
        return targets.destination
    }



    // Configs.

    /**
     * Creates a [File] pointing to a supporting files destination directory with a [directoryName].
     */
    private fun Project.getSupportingFilesDestinationDirectory(directoryName: String): File =
        buildDir.joinWithPath("supportingFiles/$directoryName")

    /** States which sources are going to be unpacked and where. */
    private inline val Project.sourcesWithUnpackDestination get() = FromTo(
        arrayOf("main.kt"),
        getSupportingFilesDestinationDirectory("sources"),
    )

    /** States which resources are going to be unpacked and where. */
    private inline val Project.resourcesWithUnpackDestination get() = FromTo(
        arrayOf("index.html"),
        getSupportingFilesDestinationDirectory("resources"),
    )

    /** States which webpack config resources are going to be unpacked and where. */
    private inline val Project.webpackConfigsWithUnpackDestination get() = FromTo(
        arrayOf(
            "webpack/1-constants.js",
            "webpack/2-shared.js",
            "webpack/development.js",
            "webpack/production.js",
        ),
        getSupportingFilesDestinationDirectory("webpackConfigs"),
    )

    /** All NPM development-only dependencies to be added. */
    private inline val devNpmDependencies: Array<Library>
        get() = with(JsVersionCatalog().libraries) {
            arrayOf(
                webpackSvgr,
                webpackHtml,
                webpackTerser,
                webpackImageminimizer,
                imageminCore,    // the minification engine to be used for the plugin above
                imageminWebp,    // WebP generation
                imageminOptipng, // lossless PNG optimization
                i18nUnused // to remove unused localizations when bundling
            )
        }

}
