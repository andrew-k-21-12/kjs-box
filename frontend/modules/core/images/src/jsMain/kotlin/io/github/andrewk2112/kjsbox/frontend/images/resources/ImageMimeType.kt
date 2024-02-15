package io.github.andrewk2112.kjsbox.frontend.images.resources

/**
 * Supported mime types for images.
 */
enum class ImageMimeType(imageFormat: String) {

    PNG("png"),
    WEBP("webp");

    /** Full mime type as a [String] value. */
    val value: String = "image/$imageFormat"

}
