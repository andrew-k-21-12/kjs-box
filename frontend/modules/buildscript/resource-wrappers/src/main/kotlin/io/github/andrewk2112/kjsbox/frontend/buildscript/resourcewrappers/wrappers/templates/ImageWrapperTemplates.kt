package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.wrappers.templates

import org.intellij.lang.annotations.Language

/**
 * Basic templates to generate the code for various image wrappers and their support sources.
 */
internal class ImageWrapperTemplates {

    @Language("kotlin")
    internal fun inflateSimpleImage(
        packageName: String,
        objectName: String,
        imageWidth: Int,
        imageHeight: Int,
        referenceName: String,
        relativeImagePath: String,
    ): String = """
package $packageName

import io.github.andrewk2112.kjsbox.frontend.images.resources.Image
import io.github.andrewk2112.kjsbox.frontend.images.resources.ImageMimeType
import io.github.andrewk2112.kjsbox.frontend.images.resources.ImageMimeType.PNG
import io.github.andrewk2112.kjsbox.frontend.images.resources.ImageMimeType.WEBP

object $objectName : Image {

    override val source: String        by ::${referenceName}PngReference    
    override val type:   ImageMimeType = PNG

    override val width  = $imageWidth
    override val height = $imageHeight

    override val alternativeSource: Image = Webp$objectName

}

private object Webp$objectName : Image {

    override val source: String        by ::${referenceName}WebpReference
    override val type:   ImageMimeType = WEBP    
    
    override val width:  Int? = null
    override val height: Int? = null

    override val alternativeSource: Image? = null    
    
}

@JsModule("./${relativeImagePath}")
@JsNonModule
private external val ${referenceName}PngReference: String

@JsModule("./${relativeImagePath}?as=webp")
@JsNonModule
private external val ${referenceName}WebpReference: String

    """.trimIndent()

}
