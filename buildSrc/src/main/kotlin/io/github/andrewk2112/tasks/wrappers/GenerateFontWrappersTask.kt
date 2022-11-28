package io.github.andrewk2112.tasks.wrappers

import io.github.andrewk2112.extensions.dotsToSlashes
import io.github.andrewk2112.extensions.joinWithPath
import io.github.andrewk2112.resources.InputResourcesWalker
import io.github.andrewk2112.resources.visitors.FontResourceVisitor
import io.github.andrewk2112.templates.FontTemplatesWriter
import io.github.andrewk2112.utility.changeMonitor
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.InvalidPathException

/**
 * Generates wrapper classes for required source fonts.
 */
abstract class GenerateFontWrappersTask : DefaultTask() {

    // Required inputs.

    /**
     * Must point to the project's resources directory.
     * Such separated configuration is needed to construct relative paths correctly.
     */
    @get:Internal
    var resourcesDir: File? by changeMonitor(::setupSrcFontsDir)

    /**
     * Must state the relative path to source fonts inside the resources.
     * Such separated configuration is needed to construct relative paths correctly.
     */
    @get:Internal
    var pathToFonts: String? by changeMonitor(::setupSrcFontsDir)

    /** A base package all generated wrappers should have. */
    @get:Input
    abstract val targetBasePackage: Property<String>

    /** Where to put the generated font wrappers. */
    @get:OutputDirectory
    abstract val outWrappers: RegularFileProperty

    /** Where to grab the source fonts from - set internally, required to check if the state is up-to-date. */
    @get:InputDirectory
    protected abstract val srcFonts: RegularFileProperty

    /**
     * Sets the [srcFonts] directory from the partial inputs.
     */
    private fun setupSrcFontsDir() {
        val resourcesDir = resourcesDir ?: return
        val pathToFonts  = pathToFonts  ?: return
        srcFonts.set(File(resourcesDir, pathToFonts))
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

        // Collecting the metadata about all required fonts and checking whether there is something to be processed.
        fontResourceVisitor.reset()
        inputFontsWalker.walk(resourcesDir!!, srcFonts.asFile.get(), fontResourceVisitor)
        if (fontResourceVisitor.fontResources.isEmpty()) return

        // Reusable entities.
        val outWrappersDir  = outWrappers.asFile.get()
        val basePackageName = targetBasePackage.get()
        val basePackagePath = basePackageName.dotsToSlashes()

        // Processing all visited source fonts - writing wrappers for them.
        for (fontResource in fontResourceVisitor.fontResources) {
            fontTemplatesWriter.writeFontStylesObject(
                outWrappersDir
                    .joinWithPath(fontResource.relativePath)
                    .joinWithPath(basePackagePath)
                    .joinWithPath(fontResource.relativePath),
                basePackageName,
                fontResource,
                "sans-serif"
            )
        }

    }

    // Processors to prepare and write font wrappers.
    private val fontResourceVisitor = FontResourceVisitor()
    private val inputFontsWalker    = InputResourcesWalker()
    private val fontTemplatesWriter = FontTemplatesWriter()

}
