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
@Suppress("FunctionName")
fun ChildrenBuilder.Image(image: Image, alternativeText: String, vararg classNames: String) {

    // This tag allows to pick the most appropriate image variant from all listed ones.
    picture {

        // Adding all available alternative sources:
        // even those which duplicate the fallback `img` one must be listed,
        // or they won't be picked when the `source` tag is supported and getting processed.
        // Also, the `source` tag picks a first-match source, so put the most specific sources first.
        image.alternativeSources?.takeIf { it.isNotEmpty() }?.forEach { alternativeSource ->
            source {
                srcSet = alternativeSource.sourceSet
                alternativeSource.type?.let { type = it.value }
            }
        }

        // A fallback image and configs shared for all variants.
        +img(ImageStyles.simpleImage.name, *classNames) {
            src = image.source // the `source` tag can be unsupported in some browsers, so the fallback `src` is a must
            loading = Loading.lazy
            alt     = alternativeText
            image.width?.let  { width  = it.toDouble() }
            image.height?.let { height = it.toDouble() }
        }

    }

}



// Private.

private object ImageStyles : DynamicStyleSheet() {

    val simpleImage: NamedRuleSet by css {
        display = Display.block // otherwise it adds some part of the font height making the overall height incorrect
    }

}
