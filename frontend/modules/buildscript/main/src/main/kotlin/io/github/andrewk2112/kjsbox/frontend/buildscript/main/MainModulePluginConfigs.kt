package io.github.andrewk2112.kjsbox.frontend.buildscript.main

import io.github.andrewk2112.utility.common.extensions.joinWithPath
import io.github.andrewk2112.utility.common.utility.FromTo
import io.github.andrewk2112.utility.gradle.extensions.createExtension
import org.gradle.api.Project
import java.io.File

/**
 * Prepares all path configs required to apply [MainModulePlugin].
 */
internal class MainModulePluginConfigs(targetProject: Project) {

    // Public.

    /** Which Kotlin sources are going to be unpacked and where. */
    val sourcesWithUnpackDestination: FromTo<Array<String>, File> by lazy {
        FromTo(
            arrayOf("main.kt"),
            supportingFilesDirectory.joinWithPath("sources")
        )
    }

    /**
     * Which resources are going to be unpacked and where.
     *
     * **Attention** - make sure to access it only when [MainModulePluginExtension] becomes available,
     * i.e. after target project's evaluation.
     */
    val resourcesWithUnpackDestination: FromTo<Array<String>, File> by lazy {
        val serviceWorkerFileName = "service-worker-source.js"
        FromTo(
            if (main.customBundleStaticsDirectory.isPresent) {
                arrayOf(serviceWorkerFileName)
            } else {
                arrayOf(defaultResourcesIndexHtmlTemplateFileName, serviceWorkerFileName)
            },
            supportingFilesDirectory.joinWithPath("resources"),
        )
    }

    /** Which webpack config resources are going to be unpacked and where. */
    val webpackConfigsWithUnpackDestination: FromTo<Array<String>, File> by lazy {
        FromTo(
            arrayOf(
                "webpack/3-shared.js",
                "webpack/development.js",
                "webpack/production.js",
            ),
            webpackConfigsDestinationDirectory,
        )
    }

    /** Where to write a file with webpack constants. */
    val webpackConstantsDestinationFile: File by lazy {
        webpackConfigsDestinationDirectory.joinWithPath("1-constants.js")
    }

    /** Where to write a file with webpack entry point configuration. */
    val webpackEntryPointConfigDestinationFile: File by lazy {
        webpackConfigsDestinationDirectory.joinWithPath("2-entry.js")
    }

    /** Where to write all webpack configs. */
    val webpackConfigsDestinationDirectory: File by lazy { supportingFilesDirectory.joinWithPath("webpackConfigs") }

    /**
     * Where to place all static sources and resources of the generated bundle.
     *
     * **Attention** - make sure to access it only when [MainModulePluginExtension] becomes available,
     * i.e. after target project's evaluation.
     */
    val bundleStaticsDirectoryName: String by lazy {
        main.customBundleStaticsDirectory.orNull ?: targetProject.version.toString()
    }

    /**
     * Path to an index HTML file from the **resources** directory.
     *
     * **Attention** - make sure to access it only when [MainModulePluginExtension] becomes available,
     * i.e. after target project's evaluation.
     */
    val indexHtmlTemplateFileName: String by lazy {
        main.customIndexHtmlTemplateFile.orNull ?: defaultResourcesIndexHtmlTemplateFileName
    }



    // Private.

    /**
     * Default index HTML file from [MainModulePlugin]'s built-in resources.
     *
     * **Attention** - do not use `"index.html"` to make webpack caching work properly for this file.
     */
    private val defaultResourcesIndexHtmlTemplateFileName = "index-template.html"

    /** Where to unpack all [MainModulePlugin]'s built-in and generated sources and resources. */
    private val supportingFilesDirectory: File = targetProject.layout.buildDirectory.asFile.get()
                                                                     .joinWithPath("supportingFiles")

    /** An extension to configure [MainModulePlugin]. */
    private val main: MainModulePluginExtension by targetProject.createExtension()

}
