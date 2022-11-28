package io.github.andrewk2112.plugins

import io.github.andrewk2112.extensions.joinWithPath
import io.github.andrewk2112.tasks.wrappers.GenerateFontWrappersTask
import io.github.andrewk2112.tasks.wrappers.GenerateImageWrappersTask
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension
import java.io.File

/**
 * Configures the way how all required resource wrappers are going to be generated.
 */
class GenerateResourceWrappersPlugin : Plugin<Project> {

    // Action.

    override fun apply(target: Project) {

        // Using the project's group with the correct format as a base package.
        val basePackage = target.group.toString().replace("-", "")

        // Accessing Kotlin-related configs.
        target.configure<KotlinJsProjectExtension> {

            // Registering wrapper tasks.
            val mainResourcesDir      = sourceSets.named("main").get().resources.srcDirs.first()
            val generateImageWrappers = target.registerImageWrappersGenerationTask(mainResourcesDir, basePackage)
            val generateFontWrappers  = target.registerFontWrappersGenerationTask(mainResourcesDir,  basePackage)

            // Generating image and font wrappers on each Gradle sync.
            target.tasks.named("prepareKotlinBuildScriptModel").get()
                        .dependsOn(generateImageWrappers, generateFontWrappers)

            // Making sure the wrappers exist before the compilation.
            target.afterEvaluate {
                target.tasks.named("compileKotlinJs").get()
                            .dependsOn(generateImageWrappers, generateFontWrappers)
            }

        }

    }



    // Private.

    /**
     * Gets a directory [File] to write all wrappers grouped by the [wrappersName].
     */
    private fun Project.getOutDirectoryForWrappers(wrappersName: String): File =
        buildDir.joinWithPath("generated/wrappers/$wrappersName")

    /**
     * Describes how to generate image wrappers for the [Project]:
     * where are the source images, what is the target package name and where to output the generated wrappers.
     *
     * @param resourcesDir Locates all available resources.
     * @param basePackage  Sets the base package for all wrappers.
     *
     * @return A fully configured and registered [GenerateImageWrappersTask].
     */
    @Throws(InvalidUserDataException::class)
    private fun Project.registerImageWrappersGenerationTask(
        resourcesDir: File,
        basePackage: String
    ): GenerateImageWrappersTask = tasks.register("generateImageWrappers", GenerateImageWrappersTask::class.java) {
        this.resourcesDir = resourcesDir
        pathToImages = "images"
        targetPackage.set("$basePackage.resources.images")
        outWrappers.set(getOutDirectoryForWrappers("images"))
        outPathToBaseInterfaces.set("core")
    }.get()

    /**
     * Describes how to generate font wrappers for the [Project]:
     * where are the source fonts, what is the target base package name and where to output the generated wrappers.
     *
     * @param resourcesDir Locates all available resources.
     * @param basePackage  Sets the base package for all wrappers.
     *
     * @return A fully configured and registered [GenerateFontWrappersTask].
     */
    @Throws(InvalidUserDataException::class)
    private fun Project.registerFontWrappersGenerationTask(
        resourcesDir: File,
        basePackage: String
    ): GenerateFontWrappersTask = tasks.register("generateFontWrappers", GenerateFontWrappersTask::class.java) {
        this.resourcesDir = resourcesDir
        pathToFonts  = "fonts"
        targetBasePackage.set("$basePackage.resources.fonts")
        outWrappers.set(getOutDirectoryForWrappers("fonts"))
    }.get()

}
