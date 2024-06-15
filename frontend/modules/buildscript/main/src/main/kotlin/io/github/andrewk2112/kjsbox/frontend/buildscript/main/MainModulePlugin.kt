package io.github.andrewk2112.kjsbox.frontend.buildscript.main

import io.github.andrewk2112.kjsbox.frontend.buildscript.shared.gradle.EntryPointModuleCallback
import io.github.andrewk2112.utility.common.extensions.joinWithPath
import io.github.andrewk2112.utility.common.utility.LazyReadOnlyProperty
import io.github.andrewk2112.utility.common.utility.FromTo
import io.github.andrewk2112.kjsbox.frontend.buildscript.versioncatalogs.JsVersionCatalog
import io.github.andrewk2112.utility.gradle.extensions.*
import org.gradle.api.*
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension
import java.io.File

/**
 * The main plugin to set up the entire environment:
 * must be applied to the central Gradle module having no own sources but containing child modules of particular types.
 */
internal class MainModulePlugin : Plugin<Project>, EntryPointModuleCallback {

    // Configs.

    /**
     * Prepares all paths and names required to apply this plugin.
     */
    private class Configs(buildDirectory: File) {

        /** States which resources are going to be unpacked and where. */
        val resourcesWithUnpackDestination: FromTo<Array<String>, File>

        /** Which sources are going to be unpacked and where. */
        val sourcesWithUnpackDestination: FromTo<Array<String>, File>

        /** Where to write all webpack configs. */
        val webpackConfigsDestinationDirectory: File

        /** States which webpack config resources are going to be unpacked and where. */
        val webpackConfigsWithUnpackDestination: FromTo<Array<String>, File>

        /** Where to write a file with webpack constants. */
        val webpackConstantsDestinationFile: File

        /** Where to write a file with webpack entry point configuration. */
        val webpackEntryPointConfigDestinationFile: File

        init {
            val supportingFilesDirectory        = buildDirectory.joinWithPath("supportingFiles")
            resourcesWithUnpackDestination      = FromTo(
                                                      arrayOf("index-template.html", "service-worker-source.js"),
                                                      supportingFilesDirectory.joinWithPath("resources"),
                                                  )
            sourcesWithUnpackDestination        = FromTo(
                                                      arrayOf("main.kt"),
                                                      supportingFilesDirectory.joinWithPath("sources")
                                                  )
            webpackConfigsDestinationDirectory  = supportingFilesDirectory.joinWithPath("webpackConfigs")
            webpackConfigsWithUnpackDestination = FromTo(
                                                      arrayOf(
                                                          "webpack/3-shared.js",
                                                          "webpack/development.js",
                                                          "webpack/production.js",
                                                      ),
                                                      webpackConfigsDestinationDirectory,
                                                  )
            webpackConstantsDestinationFile = webpackConfigsDestinationDirectory.joinWithPath("1-constants.js")
            webpackEntryPointConfigDestinationFile = webpackConfigsDestinationDirectory.joinWithPath("2-entry.js")
        }

    }



    // Action.

    @Throws(Exception::class)
    override fun apply(target: Project): Unit = target.run {

        configurableProject = target

        val jsVersionCatalog = JsVersionCatalog()

        configureNodeJs { version = jsVersionCatalog.versions.nodejs }
        configureYarn   { version = jsVersionCatalog.versions.yarn   }

        // Preparing all configs: from the configurable extension and static ones.
        val customBundleStaticsDirectory = extensions.create("main", MainModulePluginExtension::class.java)
                                                     .customBundleStaticsDirectory.orNull
        configs = Configs(layout.buildDirectory.asFile.get())

        // Registering tasks to unpack and generate all supporting files required to build the project.
        val unpackMainKt             by registerResourcesUnpackingTask(configs.sourcesWithUnpackDestination)
        val unpackIndexHtml          by registerResourcesUnpackingTask(configs.resourcesWithUnpackDestination)
        val generateWebpackConstants by registerTask<WebpackConstantsGenerationTask> {
            bundleStaticsDirectoryName.set(customBundleStaticsDirectory ?: version.toString())
            webpackConstantsOutFile.set(configs.webpackConstantsDestinationFile)
        }
        val unpackWebpackConfigs     by registerResourcesUnpackingTask(configs.webpackConfigsWithUnpackDestination)

        applyMultiplatform {
            js {
                binaries.executable() // no run or deploy tasks will be generated without this line
                browser {
                    commonWebpackConfig {
                        it.configDirectory = configs.webpackConfigsDestinationDirectory
                    }
                }
            }
            sourceSets.jsMain {
                kotlin.srcDir(unpackMainKt.outDirectory!!)       // entry point sources
                resources.srcDir(unpackIndexHtml.outDirectory!!) // entry point HTML index
                dependencies {
                    // All required compile-only NPM dependencies.
                    jsVersionCatalog.bundles.kjsboxFrontendMain.forEach { dependency ->
                        implementation(devNpm(dependency.name, dependency.version!!))
                    }
                }
            }
        }

        // This argument is very important for Yarn to make it include additional dependencies
        // required for some packages - for example, for sharp.
        tasks.withType(KotlinNpmInstallTask::class.java).configureEach {
            it.args.add("--ignore-engines")
        }
        // Making sure webpack configs and other resources are prepared before the compilation.
        afterEvaluate {
            addResourcesDependencies(unpackIndexHtml, generateWebpackConstants, unpackWebpackConfigs)
        }
        // All tasks to unpack resources are configured to provide fine-grained outputs,
        // so making sure all template sources are unpacked before the compilation.
        findTask("compileKotlinJs").dependsOn(unpackMainKt)
        // Preventing optimization issues because of the custom modules structure.
        findTask("jsBrowserDevelopmentRun").dependsOn(findTask("jsDevelopmentExecutableCompileSync"))
        findTask("jsBrowserProductionRun").dependsOn(findTask("jsProductionExecutableCompileSync"))

    }

    override fun onEntryPointModuleRegistered(entryPointModule: Project) {

        // On-demand modules require special processing to be compiled - this is the way to request the compilation.
        configurableProject.dependencies.add("jsMainImplementation", entryPointModule)

        val generateWebpackEntryPointConfig by configurableProject.registerTask<WebpackEntryPointConfigGenerationTask> {
            entryPointModuleName.set(entryPointModule.name)
            webpackEntryPointOutFile.set(configs.webpackEntryPointConfigDestinationFile)
        }

        addResourcesDependencies(generateWebpackEntryPointConfig)

    }



    // Private.

    /**
     * Makes sure [NodeJsRootPlugin] is applied and provides its [NodeJsRootExtension] for [configuration].
     */
    private fun Project.configureNodeJs(configuration: NodeJsRootExtension.() -> Unit) {
        plugins.withType(NodeJsRootPlugin::class.java) {
            extensions.findByType(NodeJsRootExtension::class.java)?.apply(configuration)
        }
    }

    /**
     * Makes sure [YarnPlugin] is applied and provides its [YarnRootExtension] for [configuration].
     */
    private fun Project.configureYarn(configuration: YarnRootExtension.() -> Unit) {
        plugins.withType(YarnPlugin::class.java) {
            extensions.findByType(YarnRootExtension::class.java)?.apply(configuration)
        }
    }

    /**
     * Registers a task to unpack built-in resources according to the provided [targets].
     */
    @Throws(IllegalStateException::class, InvalidUserDataException::class)
    private fun Project.registerResourcesUnpackingTask(
        targets: FromTo<Array<String>, File>
    ): LazyReadOnlyProperty<Any?, ResourcesUnpackingTask> =
        registerTask {
            resourceNames = targets.source
            outDirectory  = targets.destination
        }

    /**
     * Adds provided [tasks] as dependencies to be executed before resources processing.
     */
    private fun addResourcesDependencies(vararg tasks: Task) {
        configurableProject.findTask("jsProcessResources").dependsOn(*tasks)
    }

    private lateinit var configs: Configs
    private lateinit var configurableProject: Project

}
