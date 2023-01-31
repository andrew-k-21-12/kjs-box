package io.github.andrewk2112.gradle.plugins

import io.github.andrewk2112.extensions.joinWithPath
import io.github.andrewk2112.gradle.tasks.FontWrappersGenerationTask
import io.github.andrewk2112.gradle.tasks.IconWrappersGenerationTask
import io.github.andrewk2112.gradle.tasks.ImageInterfacesGenerationTask
import io.github.andrewk2112.gradle.tasks.ImageWrappersGenerationTask
import io.github.andrewk2112.gradle.tasks.WrappersGenerationTask
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.UnknownDomainObjectException
import org.gradle.api.UnknownTaskException
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension
import java.io.File

/**
 * Configures the way how all required resource wrappers are going to be generated.
 */
class ResourceWrappersGenerationPlugin : Plugin<Project> {

    // Action.

    @Throws(Exception::class)
    override fun apply(target: Project) {

        // Preparing required dependencies and registering all wrappers-generation tasks.
        val mainResourcesDirectory  = target.getMainResourcesDirectory()
        val basePackageName         = target.getPackageName()
        val interfacesPackageName   = "$basePackageName.resources.images"
        val generateFontWrappers    = target.registerWrappersGenerationTask<FontWrappersGenerationTask>(
                                          mainResourcesDirectory, basePackageName, "generateFontWrappers", "fonts"
                                      )
        val generateIconWrappers    = target.registerWrappersGenerationTask<IconWrappersGenerationTask>(
                                          mainResourcesDirectory, basePackageName, "generateIconWrappers", "icons"
                                      )
        val generateImageInterfaces = target.registerImageInterfacesGenerationTask(interfacesPackageName)
        val generateImageWrappers   = target.registerWrappersGenerationTask<ImageWrappersGenerationTask>(
                                          mainResourcesDirectory, basePackageName, "generateImageWrappers", "images"
                                      ).also {
                                          it.interfacesPackageName.set(interfacesPackageName)
                                      }

        // Generating all wrappers on each Gradle sync and before the project's compilation.
        val sourceGenerationTasks = arrayOf(
            generateFontWrappers, generateIconWrappers, generateImageInterfaces, generateImageWrappers
        )
        target.getTask("prepareKotlinBuildScriptModel") // Gradle sync
              .dependsOn(sourceGenerationTasks)
        target.afterEvaluate {
            getTask("compileKotlinJs").dependsOn(sourceGenerationTasks) // compilation
        }

    }



    // Private.

    /**
     * Gets the resources directory of the [Project]'s main build variant.
     */
    @Throws(UnknownDomainObjectException::class, IllegalStateException::class, NoSuchElementException::class)
    private fun Project.getMainResourcesDirectory(): File =
        extensions
            .getByType(KotlinJsProjectExtension::class.java)
            .sourceSets.named("main").get()
            .resources.srcDirs.first()

    /**
     * Retrieves the [Project]'s package name derived from its [Project.getGroup] value.
     */
    private fun Project.getPackageName(): String = group.toString().replace("-", "")

    /**
     * Registers a [WrappersGenerationTask] for the [Project], see [WrappersGenerationTask] for details.
     */
    @Throws(InvalidUserDataException::class, IllegalStateException::class)
    private inline fun <reified T : WrappersGenerationTask> Project.registerWrappersGenerationTask(
        allResourcesDirectory: File,
        basePackageName: String,
        taskName: String,
        relativePath: String,
    ): T =
        tasks.register(taskName, T::class.java) {
            this.allResourcesDirectory     = allResourcesDirectory
            pathToTargetResourcesDirectory = relativePath
            wrappersBasePackageName.set("$basePackageName.resources.$relativePath")
            wrappersOutDirectory.set(getSourcesGenerationOutDirectory(relativePath))
        }.get()

    /**
     * Registers an [ImageInterfacesGenerationTask] with [interfacesPackageName].
     */
    @Throws(InvalidUserDataException::class, IllegalStateException::class)
    private fun Project.registerImageInterfacesGenerationTask(
        interfacesPackageName: String
    ): ImageInterfacesGenerationTask =
        tasks.register("generateImageInterfaces", ImageInterfacesGenerationTask::class.java) {
            this.interfacesPackageName.set(interfacesPackageName)
            interfacesOutDirectory.set(getSourcesGenerationOutDirectory("imageInterfaces"))
        }.get()

    /**
     * Gets a directory [File] to write all generated sources of a [type] into.
     */
    private fun Project.getSourcesGenerationOutDirectory(type: String): File =
        buildDir.joinWithPath("generated/wrappers/$type")

    /**
     * Syntax sugar to retrieve a [Project]'s [Task] by its [name].
     */
    @Throws(UnknownTaskException::class, IllegalStateException::class)
    private fun Project.getTask(name: String): Task = tasks.named(name).get()

}
