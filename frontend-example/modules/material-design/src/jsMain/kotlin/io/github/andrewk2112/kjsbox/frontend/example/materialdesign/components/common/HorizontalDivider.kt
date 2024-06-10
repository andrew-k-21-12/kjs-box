package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.useDesignTokensContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.useMaterialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import io.github.andrewk2112.utility.react.hooks.useMemoWithReferenceCount
import kotlinx.css.*
import react.FC
import react.PropsWithClassName
import react.dom.html.ReactHTML.div



// Public.

val HorizontalDivider = FC<PropsWithClassName> { props ->

    val component = useMaterialDesignComponent()
    val styles    = useMemoWithReferenceCount(component) {
                        HorizontalDividerStyles(component.getMaterialDesignTokens())
                    }

    +div(
        styles.divider(useDesignTokensContext()).name,
        props.className.toString()
    )

}



// Private.

private class HorizontalDividerStyles(
    private val materialDesignTokens: MaterialDesignTokens
) : DynamicStyleSheet(materialDesignTokens::class) {

    val divider: DynamicCssProvider<Context> by dynamicCss {
        height = materialDesignTokens.system.sizes.stroke(it)
        backgroundColor = materialDesignTokens.system.palette.stroke1(it)
    }

}
