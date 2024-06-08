package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.wrappers.templates

import org.intellij.lang.annotations.Language

/**
 * Basic templates to generate the code for various image wrappers and their support sources.
 */
internal class ImageWrapperTemplates {

    @Language("kotlin")
    internal fun inflateSimpleImage(
        packageName: String,
        baseImageNameInCamelCase: String,
        imageExtension: String,
        imageWidth: Int,
        imageHeight: Int,
        relativeImagePath: String,
    ): String = """
package $packageName

import io.github.andrewk2112.kjsbox.frontend.image.resources.Image
import io.github.andrewk2112.kjsbox.frontend.image.resources.ImageMimeType
import io.github.andrewk2112.kjsbox.frontend.image.resources.ImageMimeType.PNG
import io.github.andrewk2112.kjsbox.frontend.image.resources.ImageMimeType.WEBP
import io.github.andrewk2112.kjsbox.frontend.image.resources.ImageSource

object $baseImageNameInCamelCase : Image {

    /** See [Image.source]. PNG is preferred as a fallback variant as it's almost guaranteed to work everywhere. */
    override val source: String by ::png${baseImageNameInCamelCase}Reference

    override val width  = $imageWidth
    override val height = $imageHeight

    /**
     * See [Image.alternativeSources].
     * Even sources duplicating a default image [source] must be listed to make all fallback variants available
     * (as `source` tags make sources from `img` ignored).
     * The most specific sources should go in the first order to be picked with a higher priority.
     */
    override val alternativeSources: Array<ImageSource> = arrayOf(Webp$baseImageNameInCamelCase, Png$baseImageNameInCamelCase)

}

private object Webp$baseImageNameInCamelCase : ImageSource {
    override val sourceSet: String by ::webp${baseImageNameInCamelCase}Reference
    override val type: ImageMimeType  = WEBP
}

private object Png$baseImageNameInCamelCase : ImageSource {
    override val sourceSet: String by ::png${baseImageNameInCamelCase}Reference
    override val type: ImageMimeType  = PNG
}

@JsModule("./${relativeImagePath}${extensionCastString("webp", imageExtension)}")
@JsNonModule
private external val webp${baseImageNameInCamelCase}Reference: String

@JsModule("./${relativeImagePath}${extensionCastString("png", imageExtension)}")
@JsNonModule
private external val png${baseImageNameInCamelCase}Reference: String

    """.trimIndent()

    /**
     * Returns a string like `"?as=`[expectedExtension]`"` if [actualExtension] is not equal to [expectedExtension]
     * or an empty string otherwise.
     */
    private fun extensionCastString(expectedExtension: String, actualExtension: String): String =
        if (actualExtension.equals(expectedExtension, ignoreCase = true)) "" else "?as=${expectedExtension}"

}
