package io.github.andrewk2112.md.components.common

import io.github.andrewk2112.components.image
import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.md.styles.StrokeColor
import io.github.andrewk2112.md.styles.StrokeConfigs
import io.github.andrewk2112.md.styles.StrokeStyles
import io.github.andrewk2112.resources.images.Image
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import kotlinx.css.Position
import kotlinx.css.inset
import kotlinx.css.position
import kotlinx.css.px
import react.FC
import react.PropsWithClassName
import react.dom.html.ReactHTML.div

// Public.

external interface StrokedImageProps : PropsWithClassName {
    var image: Image
    var alternativeText: String
}

val strokedImage = FC<StrokedImageProps> { props ->

    val context = useAppContext()

    // Wrapper to position the stroke inside the image's bounds, on top of it.
    +div(StrokedImageStyles.positioningWrapper.name) {

        // The image itself.
        image(props.image, props.alternativeText, props.className.toString())

        // Semi-transparent stroke on top of the image.
        +div(StrokedImageStyles.innerStroke(context).name)

    }

}



// Private.

private object StrokedImageStyles : DynamicStyleSheet() {

    val positioningWrapper: NamedRuleSet by css {
        position = Position.relative
    }

    val innerStroke: DynamicCssProvider<Context> by dynamicCss {
        +StrokeStyles.borderStroke(StrokeConfigs(it, StrokeColor.Intense)).rules
        position = Position.absolute
        inset(0.px)
    }

}
