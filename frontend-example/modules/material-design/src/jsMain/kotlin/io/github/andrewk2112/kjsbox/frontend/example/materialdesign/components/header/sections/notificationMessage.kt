package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.header.sections

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.core.designtokens.StyleValues
import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Theme
import io.github.andrewk2112.kjsbox.frontend.core.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles.FontStyles
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.icons.materialdesign.arrowRightThinIcon
import io.github.andrewk2112.kjsbox.frontend.core.utility.safeBlankHref
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.ChildrenBuilder
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p



// Components.

fun ChildrenBuilder.notificationMessage(
    context: Context,
    title: String,
    description: String,
    actionLabel: String,
    actionDestinationEndpoint: String
) =
    container(context) {
        titleAndDescription(context, title, description)
        actionButton(context, actionLabel, actionDestinationEndpoint)
    }

private inline fun ChildrenBuilder.container(context: Context, crossinline children: ChildrenBuilder.() -> Unit) =
    +div(clazz = NotificationMessageStyles.container(context).name, children)

private fun ChildrenBuilder.titleAndDescription(context: Context, title: String, description: String) =
    +div(clazz = NotificationMessageStyles.titleAndDescriptionWrapper.name) {
        +p(clazz = NotificationMessageStyles.title(context).name) { +title }
        +p(clazz = NotificationMessageStyles.description(context).name) { +description }
    }

private fun ChildrenBuilder.actionButton(context: Context, label: String, destinationEndpoint: String) =
    +a(clazz = NotificationMessageStyles.actionButton(context).name) {
        safeBlankHref = destinationEndpoint
        +label
        +arrowRightThinIcon(clazz = NotificationMessageStyles.actionButtonArrow.name)
    }



// Styles.

private object NotificationMessageStyles : DynamicStyleSheet() {

    val container: DynamicCssProvider<Context> by dynamicCss {
        display = Display.flex
        backgroundColor = Theme.palette.backSpecial1(it)
    }

    val titleAndDescriptionWrapper: NamedRuleSet by css { flexGrow = 1.0 }

    val title: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.bold.rules
        padding = Padding(
            top   = StyleValues.spacing.absolute25,
            left  = StyleValues.spacing.absolute40,
            right = StyleValues.spacing.absolute40,
        )
        fontSize = StyleValues.fontSizes.relative1p2
        color = Theme.palette.onBackSpecial1(it)
    }

    val description: DynamicCssProvider<Context> by dynamicCss {
        padding = Padding(
            top        = StyleValues.spacing.absolute8,
            horizontal = StyleValues.spacing.absolute40,
            bottom     = StyleValues.spacing.absolute26,
        )
        fontSize = StyleValues.fontSizes.relativep85
        color = Theme.palette.onBackSpecial1(it)
    }

    val actionButton: DynamicCssProvider<Context> by dynamicCss {
        display    = Display.flex
        alignItems = Align.center
        alignSelf  = Align.center
        flexShrink = 0
        padding = Padding(
            top    = StyleValues.spacing.absolute11p5,
            bottom = StyleValues.spacing.absolute11p5,
            left   = StyleValues.spacing.absolute15,
            right  = StyleValues.spacing.absolute18,
        )
        marginRight = StyleValues.spacing.absolute40
        borderRadius = StyleValues.radii.cornerAbsolute24
        fontSize       = StyleValues.fontSizes.relativep9
        textDecoration = TextDecoration.none
        color           = Theme.palette.onAction2(it)
        backgroundColor = Theme.palette.action2(it)
        hover {
            backgroundColor = Theme.palette.actionFocused2(it)
        }
    }

    val actionButtonArrow: NamedRuleSet by css {
        marginLeft = StyleValues.spacing.absolute12
    }

}
