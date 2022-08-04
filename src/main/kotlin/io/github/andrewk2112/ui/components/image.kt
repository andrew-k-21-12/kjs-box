package io.github.andrewk2112.ui.components

import io.github.andrewk2112.extensions.withClassName
import io.github.andrewk2112.resources.images.Image
import io.github.andrewk2112.resources.images.SimpleImage
import react.ChildrenBuilder
import react.dom.html.ImgLoading
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.picture
import react.dom.html.ReactHTML.source

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
            withClassName(img, classNames = classNames) {
                loading = ImgLoading.lazy
                width  = image.width.toDouble()
                height = image.height.toDouble()
                src = image.png
                alt = alternativeText
            }

        }

    }
}
