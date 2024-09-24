package io.github.andrewk2112.kjsbox.frontend.buildscript.main

import io.github.andrewk2112.kjsbox.frontend.buildscript.shared.gradle.EntryPointModuleCallback
import io.github.andrewk2112.kjsbox.frontend.buildscript.versioncatalogs.JsVersionCatalog
import io.github.andrewk2112.utility.gradle.extensions.applyMultiplatform
import io.github.andrewk2112.utility.gradle.extensions.configureNodeJs
import io.github.andrewk2112.utility.gradle.extensions.configureYarn
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.UnknownDomainObjectException
import org.gradle.api.plugins.UnknownPluginException
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsTargetDsl

/**
 * The main plugin to set up the entire environment:
 * must be applied to the central Gradle module having no own sources but containing child modules of particular types.
 */
internal class MainModulePlugin : Plugin<Project>, EntryPointModuleCallback {

    // Action.

    @Throws(Exception::class)
    override fun apply(target: Project) {
        configs              = MainModulePluginConfigs(target)
        configurableProject  = target
        tasks                = MainModuleTasks(target, configs)
        val jsVersionCatalog = JsVersionCatalog()
        target.run {
            applyMultiplatform {
                configureJs()
                configureSourceSets(jsVersionCatalog)
            }
            afterEvaluate {
                setJsVersions(jsVersionCatalog)
            }
        }
    }

    override fun onEntryPointModuleRegistered(entryPointModule: Project) {
        // On-demand modules require special processing to be compiled - this is the way to request the compilation.
        configurableProject.dependencies.add("jsMainImplementation", entryPointModule)
        tasks.registerWebpackEntryPointConfigGenerationTask(entryPointModule.name)
    }



    // Private.

    private fun KotlinMultiplatformExtension.configureJs() {
        js {
            this as KotlinJsTargetDsl // fixes a bug with errors highlighting in IDE
            binaries.executable()     // no run or deploy tasks will be generated without this line
            browser {
                commonWebpackConfig {
                    it.configDirectory = configs.webpackConfigsDestinationDirectory
                }
            }
        }
    }

    @Throws(NullPointerException::class)
    private fun KotlinMultiplatformExtension.configureSourceSets(jsVersionCatalog: JsVersionCatalog) {
        sourceSets.jsMain {
            // Adding sources and resources unpacked from the plugin.
            kotlin.srcDir(tasks.unpackSources.outDirectory!!)
            resources.srcDir(tasks.unpackResources.outDirectory!!)
            dependencies {
                // All required compile-only NPM dependencies.
                jsVersionCatalog.bundles.kjsboxFrontendMain.forEach { dependency ->
                    implementation(devNpm(dependency.name, dependency.version!!))
                }
            }
        }
    }

    @Throws(UnknownDomainObjectException::class, UnknownPluginException::class)
    private fun Project.setJsVersions(jsVersionCatalog: JsVersionCatalog) {
        configureNodeJs { version = jsVersionCatalog.versions.nodejs }
        configureYarn {
            version = jsVersionCatalog.versions.yarn
            // Configuring resolutions to fix security issues reported by GitHub's Dependabot.
            arrayOf(
                "@babel/traverse"  to "^7.23.2",
                "fast-loops"       to "^1.1.4",
                "ws"               to "^8.17.1",
                "braces"           to "^3.0.3",
                "ua-parser-js"     to "^1.0.33",
                "express"          to "^4.20.0",
                "follow-redirects" to "^1.15.6",
                "semver"           to "^7.0.0",
                "body-parser"      to "^1.20.3",
                "send"             to "^0.19.0",
                "serve-static"     to "^1.16.0",
                "webpack"          to "^5.94.0",
                "micromatch"       to "^4.0.8",
            ).forEach { (dependency, version) ->
                resolution(dependency, version)
            }
        }
    }

    private lateinit var configs: MainModulePluginConfigs
    private lateinit var configurableProject: Project
    private lateinit var tasks: MainModuleTasks

}
