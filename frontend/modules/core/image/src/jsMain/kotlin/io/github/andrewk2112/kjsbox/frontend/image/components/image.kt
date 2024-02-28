package io.github.andrewk2112.kjsbox.frontend.image.components

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.image.resources.Image
import kotlinx.css.Display
import kotlinx.css.display
import react.ChildrenBuilder
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.picture
import react.dom.html.ReactHTML.source
import web.html.Loading



// Public.

/**
 * Lazily renders an image in a smart way by allowing a browser to pick the most appropriate variant of it.
 *
 * @param image           An image to be rendered.
 * @param alternativeText A description to be displayed when the image fails to load.
 * @param classNames      Styling classes to be applied for the image.
 */
fun ChildrenBuilder.image(image: Image, alternativeText: String, vararg classNames: String) {

    // This tag allows to pick the most appropriate variant from the listed ones.
    picture {

        // Adding all available alternative sources.
        var nextAlternativeSource: Image? = image.alternativeSource
        while (true) {
            val alternativeSource = nextAlternativeSource ?: break
            source {
                alternativeSource.width?.let  { width  = it.toDouble() }
                alternativeSource.height?.let { height = it.toDouble() }
                alternativeSource.type?.let   { type   = it.value }
                srcSet = alternativeSource.source
            }
            nextAlternativeSource = alternativeSource.alternativeSource
        }

        // Describes the fallback variant and configs common for all variants.
        +img(ImageStyles.simpleImage.name, *classNames) {
            image.width?.let  { width  = it.toDouble() }
            image.height?.let { height = it.toDouble() }
            loading = Loading.lazy
            src = image.source
            alt = alternativeText
        }

    }

}



// Private.

private object ImageStyles : DynamicStyleSheet() {

    val simpleImage: NamedRuleSet by css {
        display = Display.block // otherwise it adds some part of the font height making the overall height incorrect
    }

}
