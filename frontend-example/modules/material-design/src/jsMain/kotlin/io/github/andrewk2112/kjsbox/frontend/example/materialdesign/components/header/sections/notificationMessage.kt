package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.header.sections

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.core.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.core.stylesheets.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.styles.FontStyles
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.icons.materialdesign.arrowRightThinIcon
import io.github.andrewk2112.kjsbox.frontend.core.utility.safeBlankHref
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.accessors.DesignTokens
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
        backgroundColor = DesignTokens.system.palette.backSpecial(it)
    }

    val titleAndDescriptionWrapper: NamedRuleSet by css { flexGrow = 1.0 }

    val title: DynamicCssProvider<Context> by dynamicCss {
        +FontStyles.bold.rules
        padding = Padding(
            top   = DesignTokens.reference.spacing.absolute25,
            left  = DesignTokens.reference.spacing.absolute40,
            right = DesignTokens.reference.spacing.absolute40,
        )
        fontSize = DesignTokens.reference.fontSizes.relative1p2
        color = DesignTokens.system.palette.onBackSpecial(it)
    }

    val description: DynamicCssProvider<Context> by dynamicCss {
        padding = Padding(
            top        = DesignTokens.reference.spacing.absolute8,
            horizontal = DesignTokens.reference.spacing.absolute40,
            bottom     = DesignTokens.reference.spacing.absolute26,
        )
        fontSize = DesignTokens.reference.fontSizes.relative0p85
        color = DesignTokens.system.palette.onBackSpecial(it)
    }

    val actionButton: DynamicCssProvider<Context> by dynamicCss {
        display    = Display.flex
        alignItems = Align.center
        alignSelf  = Align.center
        flexShrink = 0
        padding = Padding(
            top    = DesignTokens.reference.spacing.absolute11p5,
            bottom = DesignTokens.reference.spacing.absolute11p5,
            left   = DesignTokens.reference.spacing.absolute15,
            right  = DesignTokens.reference.spacing.absolute18,
        )
        marginRight = DesignTokens.reference.spacing.absolute40
        borderRadius = DesignTokens.reference.radii.cornerAbsolute24
        fontSize       = DesignTokens.reference.fontSizes.relative0p9
        textDecoration = TextDecoration.none
        color           = DesignTokens.system.palette.onAction2(it)
        backgroundColor = DesignTokens.system.palette.action2(it)
        hover {
            backgroundColor = DesignTokens.system.palette.action2Focused(it)
        }
    }

    val actionButtonArrow: NamedRuleSet by css {
        marginLeft = DesignTokens.reference.spacing.absolute12
    }

}
