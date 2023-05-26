package io.github.andrewk2112.wrappers.templates

import org.intellij.lang.annotations.Language

/**
 * Basic templates to generate the code for various image wrappers and their support sources.
 */
internal class ImageWrapperTemplates {

    @Language("kotlin")
    internal fun inflateImageInterface(packageName: String): String = """
package $packageName

sealed interface Image

    """.trimIndent()

    @Language("kotlin")
    internal fun inflateSimpleImageInterface(packageName: String): String = """
package $packageName

interface SimpleImage : Image {

    val width:  Int
    val height: Int

    val webp: String
    val png:  String

}

    """.trimIndent()

    @Language("kotlin")
    internal fun inflateSimpleImage(
        packageName: String,
        interfacesPackageName: String,
        objectName: String,
        imageWidth: Int,
        imageHeight: Int,
        referenceName: String,
        relativeImagePath: String,
    ): String = """
package $packageName

import ${interfacesPackageName}.*

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
