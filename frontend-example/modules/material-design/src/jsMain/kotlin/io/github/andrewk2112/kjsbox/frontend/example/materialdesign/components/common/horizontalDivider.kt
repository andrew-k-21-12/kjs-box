package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.common

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.core.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.accessors.DesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.hooks.useAppContext
import kotlinx.css.*
import react.FC
import react.PropsWithClassName
import react.dom.html.ReactHTML.div



// Public.

val horizontalDivider = FC<PropsWithClassName> { props ->
    +div(
        HorizontalDividerStyles.divider(useAppContext()).name,
        props.className.toString()
    )
}



// Private.

private object HorizontalDividerStyles : DynamicStyleSheet() {

    val divider: DynamicCssProvider<Context> by dynamicCss {
        height = DesignTokens.system.sizes.stroke1(it)
        backgroundColor = DesignTokens.system.palette.stroke1(it)
    }

}
