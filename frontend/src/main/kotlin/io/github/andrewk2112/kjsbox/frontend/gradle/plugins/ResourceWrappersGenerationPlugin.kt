package io.github.andrewk2112.kjsbox.frontend.gradle.plugins

import io.github.andrewk2112.kjsbox.frontend.extensions.joinWithPath
import io.github.andrewk2112.kjsbox.frontend.extensions.toValidPackage
import io.github.andrewk2112.kjsbox.frontend.gradle.tasks.*
import io.github.andrewk2112.kjsbox.frontend.utility.LazyReadOnlyProperty
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.UnknownDomainObjectException
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import java.io.File

/**
 * Configures the way how all required resource wrappers are going to be generated.
 */
class ResourceWrappersGenerationPlugin : Plugin<Project> {

    // Utility.

    /**
     * All reusable values required to register each task.
     */
    private class Context(
        val allResourcesDirectory: File,
        val basePackageName: String,
        val generatedResourcesDirectory: File,
    )

    companion object {

        /**
         * Retrieves a [File] pointing to the directory containing generated resource wrappers.
         */
        @Suppress("DeprecatedCallableAddReplaceWith")
        @Deprecated("A temporary solution, it's going to be encapsulated in future")
        fun Project.getGeneratedWrappersDirectory(): File = buildDir.joinWithPath("generated/wrappers")

        /**
         * Retrieves a package name common for all generated resource wrappers and related sources.
         */
        @Deprecated("A temporary solution, it's going to be encapsulated in future")
        fun Project.getResourcesWrappersBasePackageName(): String = "${getBasePackageName()}.resourcewrappers"

        /**
         * Retrieves the root [Project]'s package name derived from its [Project.getGroup] value.
         */
        private fun Project.getBasePackageName(): String = rootProject.group.toString().toValidPackage()

    }



    // Public - the plugin's action.

    @Throws(Exception::class)
    override fun apply(target: Project): Unit = target.run {

        // Preparing reusable dependencies.
        val mainSourceSet = getMainKotlinSourceSet()
        val context = Context(
            allResourcesDirectory       = mainSourceSet.resources.srcDirs.first(),
            basePackageName             = getResourcesWrappersBasePackageName(),
            generatedResourcesDirectory = getGeneratedResourcesDirectory()
        )

        // Registering all wrappers-generation tasks.
        val generateFontWrappers  by registerWrappersGenerationTask<FontWrappersGenerationTask>(context,  "fonts")
        val generateIconWrappers  by registerWrappersGenerationTask<IconWrappersGenerationTask>(context,  "icons")
        val generateImageWrappers by registerWrappersGenerationTask<ImageWrappersGenerationTask>(context, "images")
        generateImageWrappers.interfacesPackageName.set("${context.basePackageName}.images")
        val generateLocalizationKeys by registerWrappersGenerationTask<LocalizationKeysGenerationTask>(
            context, "locales"
        )
        val sourceGenerationTasks = arrayOf(
            generateFontWrappers, generateIconWrappers, generateImageWrappers, generateLocalizationKeys
        )

        // Adding the generated wrappers to the source set of the project.
        mainSourceSet.kotlin.srcDirs(sourceGenerationTasks.map { it.wrappersOutDirectory })

        // Just a temporary way to include all dependencies required for resource wrappers generation.
        dependencies.add("implementation", project(":core"))

        // Adding the configured resources directory to the source set of the root project.
        rootProject.run {
            getMainKotlinSourceSet().resources.srcDir(context.generatedResourcesDirectory)
            tasks.named("processResources").get()
                 .dependsOn(sourceGenerationTasks)
        }

    }



    // Private.

    /**
     * Retrieves the [Project]'s main [KotlinSourceSet].
     */
    @Throws(IllegalStateException::class, UnknownDomainObjectException::class)
    private fun Project.getMainKotlinSourceSet(): KotlinSourceSet =
        extensions
            .getByType(KotlinJsProjectExtension::class.java)
            .sourceSets.named("main").get()

    /**
     * Retrieves a [File] pointing to the directory containing the generated resources file structure.
     */
    private fun Project.getGeneratedResourcesDirectory(): File = buildDir.joinWithPath("generated/resources")

    /**
     * Registers a [WrappersGenerationTask] for the [Project], see the [WrappersGenerationTask] for details.
     */
    @Throws(IllegalStateException::class, InvalidUserDataException::class)
    private inline fun <reified T : WrappersGenerationTask> Project.registerWrappersGenerationTask(
        context: Context,
        resourcesTypeName: String,
    ) = LazyReadOnlyProperty<Any?, T> {
        tasks.register(it.name, T::class.java).get().apply {
            allResourcesDirectory  = context.allResourcesDirectory
            basePackageName        = context.basePackageName
            this.resourcesTypeName = resourcesTypeName
            moduleName             = this@registerWrappersGenerationTask.name
            generatedWrappersDir   = getGeneratedWrappersDirectory()
            resourcesOutDirectory.set(context.generatedResourcesDirectory)
        }
    }

}
