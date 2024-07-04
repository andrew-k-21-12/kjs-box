package io.github.andrewk2112.kjsbox.frontend.buildscript.main

import io.github.andrewk2112.utility.common.types.LazyPropertyDelegateProvider
import io.github.andrewk2112.utility.common.utility.FromTo
import io.github.andrewk2112.utility.gradle.extensions.findTask
import io.github.andrewk2112.utility.gradle.extensions.registerTask
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.UnknownDomainObjectException
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask
import java.io.File

/**
 * Registers and configures all tasks for [MainModulePlugin].
 */
internal class MainModuleTasks(private val targetProject: Project, private val configs: MainModulePluginConfigs) {

    // Public.

    init {
        configureNpmInstallFlags()
        setupCompilationTasksDependencies()
    }

    /**
     * When the [entryPointModuleName] becomes known,
     * registers a task to configure this entry point for webpack as well.
     */
    fun registerWebpackEntryPointConfigGenerationTask(entryPointModuleName: String) {
        val generateWebpackEntryPointConfig by targetProject.registerTask<WebpackEntryPointConfigGenerationTask> {
            this.entryPointModuleName.set(entryPointModuleName)
            webpackEntryPointOutFile.set(configs.webpackEntryPointConfigDestinationFile)
        }
        makeResourcesProcessingDependOn(generateWebpackEntryPointConfig)
    }

    /**
     * Unpacks all [MainModulePlugin]'s Kotlin built-in sources
     * according to [MainModulePluginConfigs.sourcesWithUnpackDestination].
     */
    val unpackSources: ResourcesUnpackingTask by targetProject.registerResourcesUnpackingTask(
                                                     configs.sourcesWithUnpackDestination
                                                 )

    /**
     * Unpacks all [MainModulePlugin]'s built-in resources
     * according to [MainModulePluginConfigs.resourcesWithUnpackDestination].
     */
    val unpackResources: ResourcesUnpackingTask by targetProject.registerResourcesUnpackingTask(
                                                       configs.resourcesWithUnpackDestination
                                                   )



    // Private.

    /**
     * Sets the **--ignore-engines** argument for Yarn's **install** command
     * to make it include additional dependencies required for some packages -
     * for example, for [sharp](https://sharp.pixelplumbing.com/install).
     */
    private fun configureNpmInstallFlags() {
        targetProject.tasks.withType(KotlinNpmInstallTask::class.java) {
            it.args.add("--ignore-engines")
        }
    }

    /**
     * Makes sure webpack configs and other sources and resources are prepared before the compilation.
     */
    @Throws(UnknownDomainObjectException::class)
    private fun setupCompilationTasksDependencies() {
        targetProject.run {
            afterEvaluate {
                makeResourcesProcessingDependOn(unpackResources, unpackWebpackConfigs, generateWebpackConstants)
                // Making sure all template sources are unpacked before the compilation.
                findTask("compileKotlinJs").dependsOn(unpackSources)
                // Preventing optimization issues caused by the custom modules structure.
                findTask("jsBrowserDevelopmentRun").dependsOn(findTask("jsDevelopmentExecutableCompileSync"))
                findTask("jsBrowserProductionRun").dependsOn(findTask("jsProductionExecutableCompileSync"))
            }
        }
    }

    /**
     * Registers a task to unpack built-in resources according to the provided [targets].
     */
    @Throws(IllegalStateException::class, InvalidUserDataException::class)
    private fun Project.registerResourcesUnpackingTask(
        targets: FromTo<Array<String>, File>
    ): LazyPropertyDelegateProvider<ResourcesUnpackingTask> =
        registerTask {
            resourceNames = targets.source
            outDirectory  = targets.destination
        }

    /**
     * Adds provided [tasks] as dependencies to be executed before resources processing.
     */
    @Throws(UnknownDomainObjectException::class)
    private fun makeResourcesProcessingDependOn(vararg tasks: Task) {
        targetProject.findTask("jsProcessResources").dependsOn(*tasks)
    }

    /**
     * Unpacks all [MainModulePlugin]'s built-in static webpack configs
     * according to [MainModulePluginConfigs.webpackConfigsWithUnpackDestination].
     * */
    private val unpackWebpackConfigs: ResourcesUnpackingTask by targetProject.registerResourcesUnpackingTask(
                                                                    configs.webpackConfigsWithUnpackDestination
                                                                )

    /** Generates all webpack constants according to [MainModulePluginConfigs]. */
    private val generateWebpackConstants: WebpackConstantsGenerationTask by targetProject.registerTask {
        bundleStaticsDirectoryName.set(configs.bundleStaticsDirectoryName)
        indexHtmlTemplateFileName.set(configs.indexHtmlTemplateFileName)
        webpackConstantsOutFile.set(configs.webpackConstantsDestinationFile)
    }

}
