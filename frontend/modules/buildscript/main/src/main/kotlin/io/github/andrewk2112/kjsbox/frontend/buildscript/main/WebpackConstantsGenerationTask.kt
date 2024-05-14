package io.github.andrewk2112.kjsbox.frontend.buildscript.main

import io.github.andrewk2112.utility.common.extensions.writeTo
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

/**
 * Generates a webpack configurations file containing basic constants:
 * some of these constants, for example [bundleStaticsDirectoryName], are configured by this task.
 * The generated file will be written into a provided [webpackConstantsOutFile].
 */
internal abstract class WebpackConstantsGenerationTask : DefaultTask() {

    // Configurations to be set.

    /** Defines a webpack configuration on where to output static sources and resources. */
    @get:Input
    abstract val bundleStaticsDirectoryName: Property<String>

    /** Where to write the webpack constants file. Serves as Gradle metadata to consider the task up-to-date. */
    @get:OutputFile
    abstract val webpackConstantsOutFile: RegularFileProperty



    // Action.

    @TaskAction
    @Throws(Exception::class)
    private operator fun invoke() {
        webpackConstantsContents.writeTo(webpackConstantsOutFile.asFile.get())
    }



    // Static configurations.

    @get:Throws(IllegalStateException::class)
    private inline val webpackConstantsContents: String get() = """
// Reusable constants for all build modes.
const DESTINATION_OUTPUT_DIR = "static" + "/" + "${bundleStaticsDirectoryName.get()}";
const RAW_OUTPUT_DIR         = "kotlin";
// Using another name for the original file because it gets copied on each build and overwrites its bundled version.
const RAW_TEMPLATE_PATH      = `${"$"}{RAW_OUTPUT_DIR}/index-template.html`;

    """.trimIndent()

}
