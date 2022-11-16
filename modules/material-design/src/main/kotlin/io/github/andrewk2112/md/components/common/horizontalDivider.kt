package io.github.andrewk2112.md.components.common

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import kotlinx.css.*
import react.FC
import react.PropsWithClassName
import react.dom.html.ReactHTML.div

// Public.

val horizontalDivider = FC<PropsWithClassName> { props ->

    val context = useAppContext()

    +div(HorizontalDividerStyles.horizontalDivider(context).name, props.className.toString())

}



// Private.

private object HorizontalDividerStyles : DynamicStyleSheet() {

    val horizontalDivider: DynamicCssProvider<Context> by dynamicCss {
        height = Theme.sizes.stroke1(it)
        backgroundColor = Theme.palette.stroke1(it)
    }

}
