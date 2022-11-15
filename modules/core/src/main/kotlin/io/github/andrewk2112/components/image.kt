package io.github.andrewk2112.components

import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.resources.images.Image
import io.github.andrewk2112.resources.images.SimpleImage
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import kotlinx.css.Display
import kotlinx.css.display
import react.ChildrenBuilder
import react.dom.html.ImgLoading
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.picture
import react.dom.html.ReactHTML.source

// Public.

/**
 * Lazily renders an image in a smart way by allowing a browser to pick the most appropriate variant of it.
 *
 * @param image           An image to be rendered.
 * @param alternativeText A description to be displayed when the image fails to load.
 * @param classNames      Styling classes to be applied for the image.
 * */
fun ChildrenBuilder.image(image: Image, alternativeText: String, vararg classNames: String) = when (image) {
    is SimpleImage -> {

        // This tag allows to pick the most appropriate variant from the listed ones.
        picture {

            // Let's use WebP when possible.
            source {
                srcSet = image.webp
                type   = "image/webp"
            }

            // Describes the fallback variant and configs common for all variants.
            +img(ImageStyles.simpleImage.name, *classNames) {
                loading = ImgLoading.lazy
                width  = image.width.toDouble()
                height = image.height.toDouble()
                src = image.png
                alt = alternativeText
            }

        }

    }
}



// Private.

private object ImageStyles : DynamicStyleSheet() {

    val simpleImage: NamedRuleSet by css {
        display = Display.block // otherwise it adds some part of the font height making the overall height incorrect
    }

}
