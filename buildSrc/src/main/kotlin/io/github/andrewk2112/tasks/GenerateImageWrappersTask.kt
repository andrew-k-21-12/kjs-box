package io.github.andrewk2112.tasks

import org.apache.commons.io.FilenameUtils
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.configurationcache.extensions.capitalized
import java.io.File
import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Generates wrapper classes for required source images.
 * These wrapper classes are needed to access image resources simpler and avoid boilerplate code.
 *
 * Maybe in future it will process more complicated generation cases
 * when according to image names it will generate multiple different wrapper variants
 * (extending multiple common sealed interfaces for convenience).
 * */
abstract class GenerateImageWrappersTask : DefaultTask() {

    // Public - required inputs.

    /**
     * Must point to the project's resources directory.
     * Such separated configuration is needed to construct relative paths correctly.
     * */
    @Internal
    var resourcesDir: File? = null
        set(value) {
            if (field == value) return
            field = value
            setupSrcImagesDir()
        }

    /**
     * Must state the relative path to source images inside the resources.
     * Such separated configuration is needed to construct relative paths correctly.
     * */
    @Internal
    var pathToImages: String? = null
        set(value) {
            if (field == value) return
            field = value
            setupSrcImagesDir()
        }

    /** Which package should the generated wrappers have. */
    @get:Input
    abstract val targetPackage: Property<String>

    /** Where to put the generated image wrappers. */
    @get:OutputDirectory
    abstract val outWrappers: RegularFileProperty



    // Private.

    /**
     * Sets the [srcImages] directory from the partial inputs.
     * */
    private fun setupSrcImagesDir() {
        val resourcesDir = resourcesDir ?: return
        val pathToImages = pathToImages ?: return
        srcImages.set(File(resourcesDir, pathToImages))
    }

    @TaskAction
    @Throws(IllegalArgumentException::class, IOException::class)
    private operator fun invoke() {

        // Making sure all the partial inputs are set.
        if (!srcImages.isPresent) throw IllegalArgumentException("Some required input is not set")

        // Checking if there are some images to be processed.
        val imagesToProcess = project.fileTree(srcImages).files
        if (imagesToProcess.isEmpty()) return

        // Reusable entities.
        val packageName       = targetPackage.get()
        val outDir            = File(outWrappers.asFile.get(), packageName.replace(".", "/"))
        val baseSrcImagesPath = Paths.get(srcImages.asFile.get().absolutePath)
        val resourcesPath     = Paths.get(resourcesDir?.absolutePath)

        // Making sure intermediate dirs exist.
        if (!outDir.isDirectory) {
            if (!outDir.mkdirs()) throw IOException("Can not create intermediate directories for image wrappers")
        }

        // Writing the interfaces common for all image wrappers (at least for now).
        writeCommonSealedInterface(outDir, packageName)
        writeSimpleImageInterface(outDir,  packageName)

        // Processing each source image.
        for (image in imagesToProcess) {
            val absoluteImagePath = Paths.get(image.absolutePath)
            val className         = baseSrcImagesPath.relativize(absoluteImagePath)
                                                     .generateClassName()
            val relativeImagePath = resourcesPath.relativize(absoluteImagePath).toString()
            writeImageWrapper(outDir, className, packageName, relativeImagePath)
        }

    }

    private fun List<String>.camelCase(): String = joinToString(separator = "") { it.capitalized() }

    /**
     * Generates the class name for an image wrapper using the [Path]'s path as a prefix and its base name.
     * */
    private fun Path.generateClassName(): String {
        val pathString = toString()
        val path       = FilenameUtils.getPathNoEndSeparator(pathString)
        val baseName   = FilenameUtils.getBaseName(pathString)
        return path.split("/").camelCase() +
               baseName.split("-").camelCase() +
               "Image"
    }

    /** Where to grab the source images from - set internally, required to check if the state is up-to-date. */
    @get:[Optional InputDirectory]
    protected abstract val srcImages: RegularFileProperty



    // Private - templates for classes.

    /**
     * Writes the sealed interface with the [packageName] common for all image wrappers into the [outDir].
     * */
    private fun writeCommonSealedInterface(outDir: File, packageName: String) = File(outDir, "Image.kt").writeText(
        """
        |package $packageName
        |
        |sealed interface Image
        """.trimMargin()
    )

    /**
     * Writes into the [outDir] the interface with the [packageName] representing simple images without variants.
     * */
    private fun writeSimpleImageInterface(outDir: File, packageName: String) = File(outDir, "SimpleImage.kt").writeText(
        """
        |package $packageName
        |
        |interface SimpleImage : Image {
        |    val webp: String
        |    val png:  String
        |}
        """.trimMargin()
    )

    /**
     * Writes an image wrapper for the [relativeImagePath]
     * into the [outDir] using the [className] with the [packageName].
     * */
    private fun writeImageWrapper(
        outDir: File,
        className: String,
        packageName: String,
        relativeImagePath: String
    ) = File(outDir, "$className.kt").writeText(
        """
        |package $packageName
        |
        |class $className : SimpleImage {
        |    override val webp: String = webpValueFor$className.unsafeCast<String>()
        |    override val png:  String = pngValueFor$className.unsafeCast<String>()
        |}
        |
        |@JsModule("./$relativeImagePath?as=webp")
        |@JsNonModule
        |private external val webpValueFor$className: dynamic
        |
        |@JsModule("./$relativeImagePath")
        |@JsNonModule
        |private external val pngValueFor$className: dynamic
        """.trimMargin()
    )

}
