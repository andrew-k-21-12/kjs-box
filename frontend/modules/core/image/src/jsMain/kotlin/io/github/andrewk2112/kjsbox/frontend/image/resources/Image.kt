package io.github.andrewk2112.kjsbox.frontend.image.resources

/**
 * A general image.
 */
interface Image {

    /** Full path to the image. */
    val source: String

    /** Expected width of the image if known. */
    val width: Int?

    /** Expected height of the image if known. */
    val height: Int?

    /** Optional alternative [ImageSource]s which can be picked as a better match for a particular environment. */
    val alternativeSources: Array<ImageSource>?

}
