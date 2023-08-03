package io.github.andrewk2112.kjsbox.frontend.buildscript.gradle.plugins

import io.github.andrewk2112.kjsbox.frontend.buildscript.extensions.joinWithPath
import io.github.andrewk2112.kjsbox.frontend.buildscript.gradle.extensions.kotlinJs
import io.github.andrewk2112.kjsbox.frontend.buildscript.gradle.tasks.DirectoryWritingTask
import io.github.andrewk2112.kjsbox.frontend.buildscript.gradle.tasks.EntryPointGenerationTask
import io.github.andrewk2112.kjsbox.frontend.buildscript.gradle.tasks.actions.writeintodirectory.TextWriteIntoDirectoryAction
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.intellij.lang.annotations.Language
import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper
import java.io.File



// Plugin configuration.

interface EntryPointModulePluginExtension {

    /** The full name of component to be an entry point. */
    val rootComponent: Property<String>

}



// Plugin itself.

/**
 * Must be applied to a Gradle module which is going to be an app's entry point:
 * make sure there is the only Gradle module applying it.
 */
internal class EntryPointModulePlugin : Plugin<Project> {

    // Action.

    @Throws(Exception::class)
    override fun apply(target: Project): Unit = target.run {

        plugins.apply(KotlinJsPluginWrapper::class.java)

        val generateEntryPoint = registerEntryPointGenerationTask(createEntryPointExtension())

        kotlinJs {
            js(KotlinJsCompilerType.IR).browser()
            sourceSets.named("main").get().kotlin.srcDir(generateEntryPoint)
        }

        // Entry point sources require some dependencies of this project, can be replaced with direct dependencies.
        dependencies.add("implementation", project(":core"))

        // On-demand modules require special processing to be compiled - this is the way to request the compilation.
        rootProject.dependencies.add("implementation", project)

        // There are no any clean or obvious ways to use JS-only webpack configs or configure webpack only inside Gradle,
        // so using this dirty hack with appending a special webpack-configuring JS file to set up the app's entry point.
        rootProject.tasks.named("unpackWebpackConfigs", DirectoryWritingTask::class.java) {
            it.addAction(
                TextWriteIntoDirectoryAction(generateEntryPointJsCode(), entryPointJsFileName)
            )
        }

    }



    // Private.

    /**
     * Creates and returns an extension to configure this plugin.
     */
    @Throws(IllegalArgumentException::class)
    private fun Project.createEntryPointExtension(): EntryPointModulePluginExtension =
        extensions.create(pluginConfiguratorName, EntryPointModulePluginExtension::class.java)

    /**
     * Registers and returns a task to generate entry point Kotlin sources.
     */
    @Throws(IllegalStateException::class, InvalidUserDataException::class)
    private fun Project.registerEntryPointGenerationTask(
        entryPointExtension: EntryPointModulePluginExtension
    ): EntryPointGenerationTask =
        tasks.register("generateEntryPoint", EntryPointGenerationTask::class.java) {
            it.rootComponentName.set(entryPointExtension.rootComponent)
            it.sourcesOutDirectory.set(getGeneratedEntryPointDirectory())
        }.get()



    // Configs.

    /**
     * Where to save entry point Kotlin sources.
     */
    private fun Project.getGeneratedEntryPointDirectory(): File = buildDir.joinWithPath("generated/entry")

    /**
     * Generates JS code to set up the app's entry point for webpack.
     */
    @Language("js")
    private fun Project.generateEntryPointJsCode(): String =
        "config.entry = require(\"path\").resolve(__dirname, `\${RAW_OUTPUT_DIR}/\${config.output.library}-$name.js`);\n"

    /** How to name the entry point-configuring JS file. */
    private inline val entryPointJsFileName get() = "3-entry.js"

    /** How to name the plugin-configuring scope. */
    private inline val pluginConfiguratorName get() = "entryPoint"

}
