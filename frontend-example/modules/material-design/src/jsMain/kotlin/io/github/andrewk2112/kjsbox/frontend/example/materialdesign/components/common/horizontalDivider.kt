package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common

import io.github.andrewk2112.kjsbox.frontend.core.dynamicstylesheet.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.hooks.useMemoWithReferenceCount
import io.github.andrewk2112.kjsbox.frontend.core.dynamicstylesheet.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.useDesignTokensContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.useMaterialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import kotlinx.css.*
import react.FC
import react.PropsWithClassName
import react.dom.html.ReactHTML.div



// Public.

val horizontalDivider = FC<PropsWithClassName> { props ->

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
