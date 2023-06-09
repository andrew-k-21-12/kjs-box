package io.github.andrewk2112.kjsbox.frontend.models

/**
 * Wraps all required metadata for each icon resource.
 *
 * @param name             Resource's name.
 * @param relativePath     See [HavingRelativePath.relativePath].
 * @param relativeIconPath Describes the full relative path to the icon resource
 *                         starting from the root resources directory.
 */
internal class IconResource(
    internal val name: String,
    override val relativePath: String,
    internal val relativeIconPath: String,
) : HavingRelativePath
