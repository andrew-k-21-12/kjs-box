package io.github.andrewk2112.kjsbox.frontend.buildscript.entrypoint

import io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.extensions.joinWithPath
import io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.gradle.extensions.kotlinJs
import io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.gradle.tasks.DirectoryWritingTask
import io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.gradle.tasks.actions.writeintodirectory.TextWriteIntoDirectoryAction
import io.github.andrewk2112.kjsbox.frontend.buildscript.versioncatalogs.KotlinVersionCatalog
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.intellij.lang.annotations.Language
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
            js().browser()
            sourceSets.named("main").get().kotlin.srcDir(generateEntryPoint)
        }

        // Entry point sources require some dependencies of this project, can be decomposed later.
        dependencies.add("implementation", KotlinVersionCatalog().libraries.kjsboxFrontendCore.fullNotation)

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
            it.sourcesOutDirectory.set(generatedEntryPointDirectory)
        }.get()



    // Configs.

    /**
     * Generates JS code to set up the app's entry point for webpack.
     */
    @Language("js")
    private fun Project.generateEntryPointJsCode(): String =
        "config.entry = require(\"path\").resolve(__dirname, `\${RAW_OUTPUT_DIR}/\${config.output.library}-$name.js`);\n"

    /** Where to save entry point Kotlin sources. */
    private inline val Project.generatedEntryPointDirectory: File
        get() = layout.buildDirectory.asFile.get().joinWithPath("generated/entry")

    /** How to name the entry point-configuring JS file. */
    private inline val entryPointJsFileName get() = "3-entry.js"

    /** How to name the plugin-configuring scope. */
    private inline val pluginConfiguratorName get() = "entryPoint"

}