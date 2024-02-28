package io.github.andrewk2112.kjsbox.frontend.image.resources

/**
 * Path to an image with its optional metadata and alternative images chain.
 */
interface Image {

    /** Full path to the image. */
    val source: String

    /** Image's format - can be important for alternative sources. */
    val type: ImageMimeType?

    /** Width can be `null` for alternative sources of the same size, for example. */
    val width: Int?

    /** Height can be `null` for alternative sources of the same size, for example. */
    val height: Int?

    /** Refers another image source which can be used as an alternative one (having another [type], for example). */
    val alternativeSource: Image?

}
