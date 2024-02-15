package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common.images

import io.github.andrewk2112.kjsbox.frontend.images.components.image
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.hooks.useMemoWithReferenceCount
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.useDesignTokensContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.useMaterialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component.BorderContext
import kotlinx.css.Inset
import kotlinx.css.Position
import kotlinx.css.inset
import kotlinx.css.position
import kotlinx.css.px
import react.FC
import react.dom.html.ReactHTML.div



// Public.

val strokedImage = FC<StrokedImageProps> { props ->

    val context   = useDesignTokensContext()
    val component = useMaterialDesignComponent()
    val styles    = useMemoWithReferenceCount(component) { StrokedImageStyles(component.getMaterialDesignTokens()) }

    // A wrapper to position the stroke inside the image's bounds, on top of it.
    +div(clazz = styles.positioningWrapper.name) {

        // The image itself.
        image(props.image, props.alternativeText, props.className.toString())

        // A semi-transparent stroke on top of the image.
        +div(clazz = styles.innerStroke(context).name)

    }

}



// Private.

private class StrokedImageStyles(
    private val materialDesignTokens: MaterialDesignTokens
) : DynamicStyleSheet(materialDesignTokens::class) {

    val positioningWrapper: NamedRuleSet by css {
        position = Position.relative
    }

    val innerStroke: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.component.stroke.lightBorderStroke(BorderContext(it)).rules
        position = Position.absolute
        inset    = Inset(0.px)
    }

}
