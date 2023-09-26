package io.github.andrewk2112.kjsbox.frontend.buildscript.lazymodule

import io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.extensions.joinWithPath
import io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.gradle.extensions.kotlinJs
import io.github.andrewk2112.kjsbox.frontend.buildscript.versioncatalogs.KotlinVersionCatalog
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper
import java.io.File



// Plugin configuration.

interface LazyModulePluginExtension {

    /** The full name of component to be exported as an entry point. */
    val exportedComponent: Property<String>

}



// Plugin itself.

/**
 * Describes any Gradle module which is going to be lazy (loaded on demand).
 */
internal class LazyModulePlugin : Plugin<Project> {

    // Action.

    @Throws(Exception::class)
    override fun apply(target: Project): Unit = target.run {

        plugins.apply(KotlinJsPluginWrapper::class.java)

        val generateLazyExportConfig = registerLazyExportConfigGenerationTask(createLazyModuleExtension())

        kotlinJs {
            js().browser()
            sourceSets.named("main").get().kotlin.srcDir(generateLazyExportConfig)
        }

        // Export config sources require some dependencies from this project, can be decomposed later.
        dependencies.add("implementation", KotlinVersionCatalog().libraries.kjsboxFrontendCore.fullNotation)

        // Enabling the compilation of this on-demand module.
        project.rootProject.dependencies.add("implementation", project)

    }



    // Private.

    /**
     * Creates and returns an extension to configure this plugin.
     */
    @Throws(IllegalArgumentException::class)
    private fun Project.createLazyModuleExtension(): LazyModulePluginExtension =
        extensions.create(pluginConfiguratorName, LazyModulePluginExtension::class.java)

    /**
     * Registers and returns a task to generate lazy export config's Kotlin sources.
     */
    @Throws(IllegalStateException::class, InvalidUserDataException::class)
    private fun Project.registerLazyExportConfigGenerationTask(
        lazyModuleExtension: LazyModulePluginExtension
    ): LazyExportConfigGenerationTask =
        tasks.register("generateLazyExportConfig", LazyExportConfigGenerationTask::class.java) {
            it.moduleName.set(name)
            it.componentToExport.set(lazyModuleExtension.exportedComponent)
            it.sourcesOutDirectory.set(generatedLazyExportConfigDirectory)
        }.get()



    // Configs.

    /** Where to save export-configuring Kotlin sources. */
    private inline val Project.generatedLazyExportConfigDirectory: File
        get() = layout.buildDirectory.asFile.get().joinWithPath("generated/export")

    /** How to name the plugin-configuring scope. */
    private inline val pluginConfiguratorName get() = "lazyModule"

}
