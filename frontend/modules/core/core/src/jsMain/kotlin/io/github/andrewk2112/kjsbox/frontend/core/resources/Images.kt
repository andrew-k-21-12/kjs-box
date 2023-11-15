package io.github.andrewk2112.kjsbox.frontend.core.resources

/**
 * An abstract image resource marker.
 */
sealed interface Image

/**
 * A simple image resource protocol providing just sizes and basic formats.
 */
interface SimpleImage : Image {

    val width:  Int
    val height: Int

    val webp: String
    val png:  String

}
