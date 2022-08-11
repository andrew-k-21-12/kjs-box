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
import java.awt.Dimension
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths
import javax.imageio.ImageIO
import javax.imageio.stream.FileImageInputStream

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

    /** Where to place the base interfaces inside the [outWrappers]. */
    @get:Input
    abstract val outPathToBaseInterfaces: Property<String>



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
    @Throws(IllegalArgumentException::class, IOException::class, FileNotFoundException::class)
    private operator fun invoke() {

        // Making sure all the partial inputs are set.
        if (!srcImages.isPresent) throw IllegalArgumentException("Some required input is not set")

        // Checking if there are some images to be processed.
        val imagesToProcess = project.fileTree(srcImages).files
        if (imagesToProcess.isEmpty()) return

        // Reusable entities.
        val resourcesPath  = Paths.get(resourcesDir?.absolutePath)
        val srcImagesPath  = Paths.get(srcImages.asFile.get().absolutePath)
        val outWrappersDir = outWrappers.asFile.get()
        val packageName    = targetPackage.get()
        val packagePath    = packageName.replace(".", "/")

        // Writing the interfaces.
        writeBaseInterfaces(
            File(File(outWrappersDir, outPathToBaseInterfaces.get()), packagePath),
            packageName
        )

        // Processing each source image.
        for (image in imagesToProcess) {
            val absoluteImagePath           = Paths.get(image.absolutePath)
            val resourcesRelativeImagePath  = resourcesPath.relativize(absoluteImagePath).toString()
            val imagesRelativeImagePath     = srcImagesPath.relativize(absoluteImagePath)
            val (className, pathToWrappers) = imagesRelativeImagePath.generateClassNameWithPath()
            val outDir = File(File(outWrappersDir, pathToWrappers), packagePath)
            if (!outDir.isDirectory) {
                if (!outDir.mkdirs()) throw IOException("Can not create the output directory for an image wrapper")
            }
            writeImageWrapper(outDir, className, packageName, readImageSize(image), resourcesRelativeImagePath)
        }

    }

    /**
     * Performs checks and writes all required interfaces with the [packageName] into the [outDir].
     * */
    @Throws(IOException::class, FileNotFoundException::class)
    private fun writeBaseInterfaces(outDir: File, packageName: String) {

        // Making sure the destination dir exist.
        if (!outDir.isDirectory) {
            if (!outDir.mkdirs()) throw IOException("Can not create the output directory for the base interfaces")
        }

        // Writing the interfaces common for all image wrappers (at least for now).
        writeCommonSealedInterface(outDir, packageName)
        writeSimpleImageInterface(outDir,  packageName)

    }

    private fun List<String>.camelCase(): String = joinToString(separator = "") { it.capitalized() }

    /**
     * Generates the class name for an image wrapper using the [Path]'s path as a prefix and its base name,
     * also returns the relative path to be used for this group of image wrappers.
     * */
    private fun Path.generateClassNameWithPath(): Pair<String, String> {
        val pathString = toString()
        val path       = FilenameUtils.getPathNoEndSeparator(pathString)
        val baseName   = FilenameUtils.getBaseName(pathString)
        return Pair(
            path.split("/").camelCase() + baseName.split("-").camelCase() + "Image",
            path
        )
    }

    /**
     * Reads the [Dimension] of an [image] without entirely loading it into the memory.
     * */
    @Throws(IllegalArgumentException::class, FileNotFoundException::class, IOException::class)
    private fun readImageSize(image: File): Dimension {
        val imageReaders = ImageIO.getImageReadersBySuffix(image.extension)
        while (imageReaders.hasNext()) {
            val imageReader = imageReaders.next()
            var inputStream: FileImageInputStream? = null
            try {
                inputStream = FileImageInputStream(image)
                imageReader.input = inputStream
                return Dimension(
                    imageReader.getWidth(imageReader.minIndex),
                    imageReader.getHeight(imageReader.minIndex)
                )
            } finally {
                imageReader.dispose()
                inputStream?.close()
            }
        }
        throw IOException("Not a known image file: ${image.absolutePath}")
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
        |
        |    val width:  Int
        |    val height: Int
        |
        |    val webp: String
        |    val png:  String
        |    
        |}
        """.trimMargin()
    )

    /**
     * Writes an image wrapper for the [relativeImagePath] with the [imageSize]
     * into the [outDir] using the [className] with the [packageName].
     * */
    private fun writeImageWrapper(
        outDir: File,
        className: String,
        packageName: String,
        imageSize: Dimension,
        relativeImagePath: String
    ) = File(outDir, "$className.kt").writeText(
        """
        |package $packageName
        |
        |object $className : SimpleImage {
        |
        |    override val width  = ${imageSize.width}
        |    override val height = ${imageSize.height}
        |
        |    override val webp: String = webpQueryFor$className.unsafeCast<String>()
        |    override val png:  String = pngQueryFor$className.unsafeCast<String>()
        |    
        |}
        |
        |@JsModule("./$relativeImagePath?as=webp")
        |@JsNonModule
        |private external val webpQueryFor$className: dynamic
        |
        |@JsModule("./$relativeImagePath")
        |@JsNonModule
        |private external val pngQueryFor$className: dynamic
        """.trimMargin()
    )

}
