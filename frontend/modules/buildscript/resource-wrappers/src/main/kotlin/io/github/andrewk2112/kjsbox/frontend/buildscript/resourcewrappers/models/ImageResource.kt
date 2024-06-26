package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.models

import java.awt.Dimension

/**
 * Wraps all required metadata for each image resource.
 *
 * @param name              Resource's name.
 * @param extension         Resource's extension.
 * @param relativePath      See [HavingRelativePath.relativePath].
 * @param relativeImagePath Describes the full relative path to the image resource
 *                          starting from the root resources directory.
 * @param imageSize         A [Dimension] of this image resource.
 */
internal class ImageResource(
    internal val name: String,
    internal val extension: String,
    override val relativePath: String,
    internal val relativeImagePath: String,
    internal val imageSize: Dimension
) : HavingRelativePath
