package io.github.andrewk2112.md.components.menu

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.md.styles.StrokeColor.DEFAULT
import io.github.andrewk2112.md.styles.StrokeConfigs
import io.github.andrewk2112.md.styles.StrokePosition.BOTTOM
import io.github.andrewk2112.md.styles.StrokeStyles
import kotlinx.css.*
import react.ChildrenBuilder
import react.dom.html.ReactHTML.div

fun ChildrenBuilder.menuDivider(context: Context) = +div(MenuDividerStyles.divider(context).name)

private object MenuDividerStyles : DynamicStyleSheet() {

    val divider: DynamicCssProvider<Context> by dynamicCss {
        +StrokeStyles.borderStroke(StrokeConfigs(it, DEFAULT, BOTTOM)).rules
        marginTop = StyleValues.spacing.absolute40
    }

}
