package io.github.andrewk2112.kjsbox.frontend.image.resources

/**
 * Describes one of image sources - an image variant can be selected according to some criteria.
 */
interface ImageSource {

    /** Full path to the image or a list of possible images with their selection criteria. */
    val sourceSet: String

    /** Image's format. */
    val type: ImageMimeType?

}
