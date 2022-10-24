package io.github.andrewk2112.md.components.header

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.stylesheets.NamedRuleSet
import io.github.andrewk2112.extensions.withClassName
import io.github.andrewk2112.md.resources.iconArrowRightThin
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.ChildrenBuilder
import react.dom.html.AnchorTarget
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p

// Public.

fun ChildrenBuilder.headerNotification(
    context: Context,
    title: String,
    description: String,
    actionLabel: String,
    actionDestinationEndpoint: String
) {

    withClassName(div, HeaderNotificationStyles.container(context).name) {

        // Title and description rows.
        withClassName(div, HeaderNotificationStyles.titleAndDescriptionRows.name) {

            // Title.
            withClassName(p, HeaderNotificationStyles.title(context).name) { +title }

            // Description.
            withClassName(p, HeaderNotificationStyles.description(context).name) { +description }

        }

        // Action button.
        withClassName(a, HeaderNotificationStyles.actionButton(context).name) {

            target = AnchorTarget._blank
            href   = actionDestinationEndpoint

            +actionLabel

            // Arrow icon.
            withClassName(iconArrowRightThin.component, HeaderNotificationStyles.actionButtonArrow.name) {}

        }

    }

}



// Private.

private object HeaderNotificationStyles : DynamicStyleSheet() {

    val container: DynamicCssProvider<Context> by dynamicCss {
        display         = Display.flex
        backgroundColor = Theme.palette.backSpecial1(it)
    }

    val titleAndDescriptionRows: NamedRuleSet by css { flexGrow = 1.0 }

    val title: DynamicCssProvider<Context> by dynamicCss {
        paddingTop   = StyleValues.spacing.absolute25
        paddingLeft  = StyleValues.spacing.absolute40
        paddingRight = StyleValues.spacing.absolute40
        fontSize     = Theme.fontSizes.adaptive2(it)
        color        = Theme.palette.onBackSpecial1(it)
    }

    val description: DynamicCssProvider<Context> by dynamicCss {
        paddingTop    = StyleValues.spacing.absolute8
        paddingLeft   = StyleValues.spacing.absolute40
        paddingRight  = StyleValues.spacing.absolute40
        paddingBottom = StyleValues.spacing.absolute26
        fontSize      = Theme.fontSizes.adaptive1(it)
        color         = Theme.palette.onBackSpecial1(it)
    }

    val actionButton: DynamicCssProvider<Context> by dynamicCss {
        display    = Display.flex
        alignItems = Align.center
        alignSelf  = Align.center
        flexShrink = 0.0
        paddingTop    = StyleValues.spacing.absolute11p5
        paddingBottom = StyleValues.spacing.absolute11p5
        paddingLeft   = StyleValues.spacing.absolute15
        paddingRight  = StyleValues.spacing.absolute18
        marginRight   = StyleValues.spacing.absolute40
        borderRadius  = StyleValues.radii.cornerAbsolute24
        fontSize       = StyleValues.fontSizes.relativep90
        textDecoration = TextDecoration.none
        backgroundColor = Theme.palette.action2(it)
        hover {
            backgroundColor = Theme.palette.actionFocused2(it)
        }
        color = Theme.palette.onAction2(it)
    }

    val actionButtonArrow: NamedRuleSet by css {
        marginLeft = StyleValues.spacing.absolute12
    }

}
