package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.core.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.hooks.useMemoWithReferenceCount
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useAppContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.materialDesignComponentContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import kotlinx.css.*
import react.FC
import react.PropsWithClassName
import react.dom.html.ReactHTML.div
import react.useContext



// Public.

val horizontalDivider = FC<PropsWithClassName> { props ->

    val component = useContext(materialDesignComponentContext)
    val styles    = useMemoWithReferenceCount(component) {
                        HorizontalDividerStyles(component.getMaterialDesignTokens())
                    }

    +div(
        styles.divider(useAppContext()).name,
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
