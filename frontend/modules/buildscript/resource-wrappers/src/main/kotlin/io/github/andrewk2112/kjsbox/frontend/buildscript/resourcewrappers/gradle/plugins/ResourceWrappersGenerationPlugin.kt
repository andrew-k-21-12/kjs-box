package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.plugins

import io.github.andrewk2112.utility.common.extensions.joinWithPath
import io.github.andrewk2112.utility.common.utility.LazyReadOnlyProperty
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.gradle.tasks.*
import io.github.andrewk2112.kjsbox.frontend.buildscript.versioncatalogs.KotlinVersionCatalog
import io.github.andrewk2112.utility.gradle.extensions.*
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.UnknownDomainObjectException
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import java.io.File

/**
 * Configures the way how all required resource wrappers are going to be generated.
 */
internal class ResourceWrappersGenerationPlugin : Plugin<Project> {

    // Configs.

    /**
     * All reusable configs required to register each task.
     */
    private class Configs @Throws(IllegalStateException::class, NoSuchElementException::class) constructor(
        project: Project,
        jsMainKotlinSourceSet: KotlinSourceSet
    ) {

        /** The root directory of all resources. */
        val allResourcesDirectory: File = jsMainKotlinSourceSet.resources.srcDirs.first()

        /** A package name common for all generated resource wrappers. */
        val basePackageName: String = "${project.rootProject.getPossiblePackageName()}.resourcewrappers"

        /** A [File] pointing to the directory containing generated resource wrappers. */
        val generatedWrappersDirectory: File = project.layout.buildDirectory.asFile.get()
                                                             .joinWithPath("generated/wrappers")

        /** A [File] pointing to the directory containing generated resources file structure. */
        val generatedResourcesDirectory: File = project.layout.buildDirectory.asFile.get()
                                                              .joinWithPath("generated/resources")

    }



    // Public - the plugin's action.

    @Throws(Exception::class)
    override fun apply(target: Project): Unit = target.run {

        // Preparing reusable dependencies.
        val jsMainKotlinSourceSet = getJsMainKotlinSourceSet()
        val configs               = Configs(this, jsMainKotlinSourceSet)

        // Registering all source-generating tasks.
        val generateFontWrappers     by registerWrappersGenerationTask<FontWrappersGenerationTask>(configs,     "fonts")
        val generateIconWrappers     by registerWrappersGenerationTask<IconWrappersGenerationTask>(configs,     "icons")
        val generateImageWrappers    by registerWrappersGenerationTask<ImageWrappersGenerationTask>(configs,    "images")
        val generateLocalizationKeys by registerWrappersGenerationTask<LocalizationKeysGenerationTask>(configs, "locales")
        val sourceGenerationTasks = arrayOf(
            generateFontWrappers, generateIconWrappers, generateImageWrappers, generateLocalizationKeys
        )

        // Adding the generated wrappers to the source set of project.
        jsMainKotlinSourceSet.kotlin.srcDirs(sourceGenerationTasks.map { it.wrappersOutDirectory })

        // Including all dependencies required for generated resource wrappers.
        jsMainKotlinSourceSet.dependencies {
            val kotlinVersionCatalog = KotlinVersionCatalog()
            implementation(kotlinVersionCatalog.libraries.kjsboxFrontendDynamicstylesheet.fullNotation)
            implementation(kotlinVersionCatalog.libraries.kjsboxFrontendImages.fullNotation)
        }

        // Adding the configured resources directory to the source set of the root project.
        rootProject.run {
            getJsMainKotlinSourceSet().resources.srcDir(configs.generatedResourcesDirectory)
            findTask("jsProcessResources").dependsOn(sourceGenerationTasks)
        }

    }



    // Private.

    /**
     * Retrieves the [Project]'s JS main [KotlinSourceSet].
     */
    @Throws(IllegalStateException::class, UnknownDomainObjectException::class)
    private fun Project.getJsMainKotlinSourceSet(): KotlinSourceSet =
        getExtension<KotlinMultiplatformExtension>().sourceSets.jsMain()

    /**
     * Registers a [WrappersGenerationTask] for the [Project], see the [WrappersGenerationTask] for details.
     */
    @Throws(IllegalStateException::class, InvalidUserDataException::class)
    private inline fun <reified T : WrappersGenerationTask> Project.registerWrappersGenerationTask(
        configs: Configs,
        resourcesTypeName: String,
    ): LazyReadOnlyProperty<Any?, T> = registerTask {
        allResourcesDirectory  = configs.allResourcesDirectory
        basePackageName        = configs.basePackageName
        this.resourcesTypeName = resourcesTypeName
        moduleName             = this@registerWrappersGenerationTask.name
        generatedWrappersDir   = configs.generatedWrappersDirectory
        resourcesOutDirectory.set(configs.generatedResourcesDirectory)
    }

}
