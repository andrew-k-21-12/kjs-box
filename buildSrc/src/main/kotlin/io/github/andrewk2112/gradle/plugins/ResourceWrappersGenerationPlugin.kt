package io.github.andrewk2112.gradle.plugins

import io.github.andrewk2112.extensions.joinWithPath
import io.github.andrewk2112.gradle.tasks.FontWrappersGenerationTask
import io.github.andrewk2112.gradle.tasks.IconWrappersGenerationTask
import io.github.andrewk2112.gradle.tasks.ImageWrappersGenerationTask
import io.github.andrewk2112.gradle.tasks.WrappersGenerationTask
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.UnknownDomainObjectException
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import java.io.File
import io.github.andrewk2112.utility.LazyReadOnlyProperty

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
        val moduleNameSubPackage: String,
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
        private fun Project.getBasePackageName(): String = rootProject.group.toString().replace("-", "")

    }



    // Public - the plugin's action.

    @Throws(Exception::class)
    override fun apply(target: Project): Unit = target.run {

        // Preparing reusable dependencies.
        val mainSourceSet = getMainKotlinSourceSet()
        val context = Context(
            allResourcesDirectory       = mainSourceSet.resources.srcDirs.first(),
            basePackageName             = getResourcesWrappersBasePackageName(),
            moduleNameSubPackage        = name.replace("-", ""),
            generatedResourcesDirectory = getGeneratedResourcesDirectory()
        )

        // Registering all wrappers-generation tasks.
        val generateFontWrappers  by registerWrappersGenerationTask<FontWrappersGenerationTask>(context,  "fonts")
        val generateIconWrappers  by registerWrappersGenerationTask<IconWrappersGenerationTask>(context,  "icons")
        val generateImageWrappers by registerWrappersGenerationTask<ImageWrappersGenerationTask>(context, "images")
        generateImageWrappers.interfacesPackageName.set("${context.basePackageName}.images")
        val sourceGenerationTasks = arrayOf(generateFontWrappers, generateIconWrappers, generateImageWrappers)

        // Adding the generated wrappers to the source set of the project.
        mainSourceSet.kotlin.srcDirs(sourceGenerationTasks.map { it.wrappersOutDirectory })

        // Adding the configured resources directory to the source set of the root project.
        rootProject.run {
            getMainKotlinSourceSet().resources.srcDir(context.generatedResourcesDirectory)
            tasks.named("processResources") {
                dependsOn(sourceGenerationTasks)
            }
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
        tasks.register(it.name, T::class.java) {
            targetResourcesDirectory.set(context.allResourcesDirectory.joinWithPath(resourcesTypeName))
            wrappersBasePackageName.set("${context.basePackageName}.$resourcesTypeName.${context.moduleNameSubPackage}")
            subPathToBundledResources.set("$resourcesTypeName/${context.moduleNameSubPackage}")
            wrappersOutDirectory.set(getGeneratedWrappersDirectory().joinWithPath(resourcesTypeName))
            resourcesOutDirectory.set(context.generatedResourcesDirectory)
        }.get()
    }

}
