package io.github.andrewk2112.ui.components.md.menu

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.stylesheets.DynamicCssProvider
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.extensions.withClassName
import io.github.andrewk2112.ui.styles.StrokeColor.DEFAULT
import io.github.andrewk2112.ui.styles.StrokeConfigs
import io.github.andrewk2112.ui.styles.StrokePosition.BOTTOM
import io.github.andrewk2112.ui.styles.StrokeStyles
import kotlinx.css.*
import react.ChildrenBuilder
import react.dom.html.ReactHTML.div

fun ChildrenBuilder.menuDivider(context: Context) = withClassName(div, MenuDividerStyles.divider(context).name) {}

private object MenuDividerStyles : DynamicStyleSheet() {

    val divider: DynamicCssProvider<Context> by dynamicCss {
        +StrokeStyles.borderStroke(StrokeConfigs(it, DEFAULT, BOTTOM)).rules
        marginTop = StyleValues.spacing.absolute40
    }

}
