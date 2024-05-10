package io.github.andrewk2112.kjsbox.frontend.buildscript.main

import io.github.andrewk2112.utility.common.extensions.writeTo
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

/**
 * Generates a small webpack configurations file declaring the app's entry point module.
 */
internal abstract class WebpackEntryPointConfigGenerationTask : DefaultTask() {

    // Configurations to be set.

    /** Defines a name of module to be used as an entry point. */
    @get:Input
    abstract val entryPointModuleName: Property<String>

    /** Where to write the entry point-configuring webpack file. */
    @get:OutputFile
    abstract val webpackEntryPointOutFile: RegularFileProperty



    // Action.

    @TaskAction
    @Throws(Exception::class)
    private operator fun invoke() {
        webpackEntryPointJsCode.writeTo(webpackEntryPointOutFile.asFile.get())
    }



    // Static configurations.

    /** JS code to set up the app's entry point for webpack. */
    @get:Throws(IllegalStateException::class)
    private inline val webpackEntryPointJsCode: String get() = """
config.entry = require("path").resolve(__dirname, `${'$'}{RAW_OUTPUT_DIR}/${'$'}{config.output.library}-${entryPointModuleName.get()}.js`);

    """.trimIndent()

}
