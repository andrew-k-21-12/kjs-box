package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.plugins

import io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.extensions.joinWithPath
import io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.extensions.toValidPackage
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks.*
import io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.utility.LazyReadOnlyProperty
import io.github.andrewk2112.kjsbox.frontend.buildscript.versioncatalogs.KotlinVersionCatalog
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
        val generatedWrappersDirectory: File,
        val generatedResourcesDirectory: File,
    )



    // Public - the plugin's action.

    @Throws(Exception::class)
    override fun apply(target: Project): Unit = target.run {

        // Preparing reusable dependencies.
        val mainSourceSet = getMainKotlinSourceSet()
        val context = Context(
            allResourcesDirectory       = mainSourceSet.resources.srcDirs.first(),
            basePackageName             = resourcesWrappersBasePackageName,
            generatedWrappersDirectory  = generatedWrappersDirectory,
            generatedResourcesDirectory = generatedResourcesDirectory
        )

        // Registering all wrappers-generation tasks.
        val generateFontWrappers     by registerWrappersGenerationTask<FontWrappersGenerationTask>(context,  "fonts")
        val generateIconWrappers     by registerWrappersGenerationTask<IconWrappersGenerationTask>(context,  "icons")
        val generateImageWrappers    by registerWrappersGenerationTask<ImageWrappersGenerationTask>(context, "images")
        val generateLocalizationKeys by registerWrappersGenerationTask<LocalizationKeysGenerationTask>(
            context, "locales"
        )
        val sourceGenerationTasks = arrayOf(
            generateFontWrappers, generateIconWrappers, generateImageWrappers, generateLocalizationKeys
        )

        // Adding the generated wrappers to the source set of the project.
        mainSourceSet.kotlin.srcDirs(sourceGenerationTasks.map { it.wrappersOutDirectory })

        // Including all dependencies required for resource wrappers generation.
        dependencies.add("implementation", KotlinVersionCatalog().libraries.kjsboxFrontendCore.fullNotation)

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
            generatedWrappersDir   = context.generatedWrappersDirectory
            resourcesOutDirectory.set(context.generatedResourcesDirectory)
        }
    }

    /** A package name common for all generated resource wrappers. */
    private inline val Project.resourcesWrappersBasePackageName: String
        get() = "${rootProject.group.toString().toValidPackage()}.${rootProject.name.replace('-', '.')}.resourcewrappers"

    /** A [File] pointing to the directory containing generated resource wrappers. */
    private inline val Project.generatedWrappersDirectory: File
        get() = layout.buildDirectory.asFile.get().joinWithPath("generated/wrappers")

    /** A [File] pointing to the directory containing the generated resources file structure. */
    private inline val Project.generatedResourcesDirectory: File
        get() = layout.buildDirectory.asFile.get().joinWithPath("generated/resources")

}
