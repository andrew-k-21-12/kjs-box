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

import io.github.andrewk2112.kjsbox.frontend.resources.SimpleImage

object $objectName : SimpleImage {

    override val width  = $imageWidth
    override val height = $imageHeight

    override val webp: String = ${referenceName}WebpReference.unsafeCast<String>()
    override val png:  String = ${referenceName}PngReference.unsafeCast<String>()

}

@JsModule("./${relativeImagePath}?as=webp")
@JsNonModule
private external val ${referenceName}WebpReference: dynamic

@JsModule("./${relativeImagePath}")
@JsNonModule
private external val ${referenceName}PngReference: dynamic

    """.trimIndent()

}
