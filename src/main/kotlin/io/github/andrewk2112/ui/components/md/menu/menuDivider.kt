package io.github.andrewk2112.ui.components.md.menu

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.ui.styles.StrokeConfigs
import io.github.andrewk2112.ui.styles.StrokePosition
import io.github.andrewk2112.ui.styles.StrokeStyles
import kotlinx.css.*
import react.RBuilder
import react.dom.div

fun RBuilder.menuDivider(context: Context) = div(MenuDividerStyles.divider(context).name) {}

private object MenuDividerStyles : DynamicStyleSheet() {

    val divider by dynamicCss<Context> {
        +StrokeStyles.defaultStroke(StrokeConfigs(it, StrokePosition.BOTTOM)).rules
        marginTop = StyleValues.spacing.absolute40
    }

}
