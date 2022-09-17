package io.github.andrewk2112.resources.visitors

import io.github.andrewk2112.models.ImageResource
import io.github.andrewk2112.utility.ImageMetadataReader
import org.apache.commons.io.FilenameUtils
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.InvalidPathException
import java.nio.file.Path

/**
 * A [ResourceVisitor] to collect all [ImageResource] metadata.
 * */
internal class ImageResourceVisitor(
    private val imageMetadataReader: ImageMetadataReader = ImageMetadataReader()
) : ResourceVisitor {

    @Throws(
        InvalidPathException::class,
        IllegalArgumentException::class,
        SecurityException::class,
        FileNotFoundException::class,
        IOException::class
    )
    override fun visit(rootResourcesPath: Path, targetResourcesPath: Path, resourceFile: File) {

        // Preparing various required metadata.
        val name      = resourceFile.nameWithoutExtension
        val imageSize = imageMetadataReader.readDimension(resourceFile)

        // Preparing relative paths.
        val absoluteResourcePath        = resourceFile.toPath()
        val rootRelativePath            = rootResourcesPath.relativize(absoluteResourcePath).toString()
        val targetRelativePath          = targetResourcesPath.relativize(absoluteResourcePath).toString()
        val pathFromTargetDirToResource = FilenameUtils.getPathNoEndSeparator(targetRelativePath)

        // Completing the resource visiting.
        _imageResources.add(
            ImageResource(name, pathFromTargetDirToResource, rootRelativePath, imageSize)
        )

    }

    /**
     * Clears all previously collected metadata about [ImageResource]s.
     * */
    internal fun reset() = _imageResources.clear()

    /** Provides collected metadata for all [ImageResource]s. */
    internal val imageResources: List<ImageResource> get() = _imageResources
    private val _imageResources = mutableListOf<ImageResource>()

}
