package io.github.andrewk2112.tasks.wrappers

import io.github.andrewk2112.extensions.dotsToSlashes
import io.github.andrewk2112.extensions.joinWithPath
import io.github.andrewk2112.resources.InputResourcesWalker
import io.github.andrewk2112.resources.visitors.ImageResourceVisitor
import io.github.andrewk2112.templates.ImageTemplatesWriter
import io.github.andrewk2112.utility.changeMonitor
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.InvalidPathException

/**
 * Generates wrapper classes for required source images.
 * These wrapper classes are needed to access image resources simpler and avoid boilerplate code.
 *
 * Maybe in future it will process more complicated generation cases
 * when according to image names it will generate multiple different wrapper variants
 * (extending multiple common sealed interfaces for convenience).
 */
abstract class GenerateImageWrappersTask : DefaultTask() {

    // Required setups.

    /**
     * Must point to the project's resources directory.
     * Such separated configuration is needed to construct relative paths correctly.
     */
    @get:Internal
    var resourcesDir: File? by changeMonitor(::setupSrcImagesDir)

    /**
     * Must state the relative path to source images inside the resources.
     * Such separated configuration is needed to construct relative paths correctly.
     */
    @get:Internal
    var pathToImages: String? by changeMonitor(::setupSrcImagesDir)

    /** Which package should the generated wrappers have. */
    @get:Input
    abstract val targetPackage: Property<String>

    /** Where to put the generated image wrappers. */
    @get:OutputDirectory
    abstract val outWrappers: RegularFileProperty

    /** Where to place the base interfaces inside the [outWrappers]. */
    @get:Input
    abstract val outPathToBaseInterfaces: Property<String>

    /** Where to grab the source images from - set internally, required to check if the state is up-to-date. */
    @get:InputDirectory
    protected abstract val srcImages: RegularFileProperty

    /**
     * Sets the [srcImages] directory from the partial inputs.
     */
    private fun setupSrcImagesDir() {
        val resourcesDir = resourcesDir ?: return
        val pathToImages = pathToImages ?: return
        srcImages.set(File(resourcesDir, pathToImages))
    }



    // The action to be executed, private.

    @TaskAction
    @Throws(
        IllegalArgumentException::class,
        SecurityException::class,
        InvalidPathException::class,
        IOException::class,
        FileNotFoundException::class
    )
    private operator fun invoke() {

        // Collecting the metadata about all required images and checking whether there is something to be processed.
        imageResourceVisitor.reset()
        inputImagesWalker.walk(resourcesDir!!, srcImages.asFile.get(), imageResourceVisitor)
        if (imageResourceVisitor.imageResources.isEmpty()) return

        // Reusable entities.
        val outWrappersDir = outWrappers.asFile.get()
        val packageName    = targetPackage.get()
        val packagePath    = packageName.dotsToSlashes()

        // Writing the interfaces.
        imageTemplatesWriter.writeBaseInterfaces(
            outWrappersDir
                .joinWithPath(outPathToBaseInterfaces.get())
                .joinWithPath(packagePath),
            packageName
        )

        // Processing each visited source image - writing a wrapper for it.
        for (imageResource in imageResourceVisitor.imageResources) {
            imageTemplatesWriter.writeSimpleImageObject(
                outWrappersDir
                    .joinWithPath(imageResource.relativePath)
                    .joinWithPath(packagePath),
                packageName,
                imageResource
            )
        }

    }

    // Processors to prepare and write image wrappers.
    private val imageResourceVisitor = ImageResourceVisitor()
    private val inputImagesWalker    = InputResourcesWalker()
    private val imageTemplatesWriter = ImageTemplatesWriter()

}
