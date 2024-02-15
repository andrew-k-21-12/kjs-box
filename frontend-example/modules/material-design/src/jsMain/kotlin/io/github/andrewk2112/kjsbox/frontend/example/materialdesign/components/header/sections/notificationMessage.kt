package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.header.sections

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.core.hooks.useMemoWithReferenceCount
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.NamedRuleSet
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.icons.materialdesign.arrowRightThinIcon
import io.github.andrewk2112.kjsbox.frontend.core.utility.safeBlankHref
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.useMaterialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
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
) {

    val component = useMaterialDesignComponent()
    val styles = useMemoWithReferenceCount(component) { NotificationMessageStyles(component.getMaterialDesignTokens()) }

    container(context, styles) {
        titleAndDescription(context, styles, title, description)
        actionButton(context, styles, actionLabel, actionDestinationEndpoint)
    }

}

private inline fun ChildrenBuilder.container(
    context: Context,
    styles: NotificationMessageStyles,
    crossinline children: ChildrenBuilder.() -> Unit
) =
    +div(clazz = styles.container(context).name, children)

private fun ChildrenBuilder.titleAndDescription(
    context: Context, styles: NotificationMessageStyles, title: String, description: String
) =
    +div(clazz = styles.titleAndDescriptionWrapper.name) {
        +p(clazz = styles.title(context).name) { +title }
        +p(clazz = styles.description(context).name) { +description }
    }

private fun ChildrenBuilder.actionButton(
    context: Context, styles: NotificationMessageStyles, label: String, destinationEndpoint: String
) =
    +a(clazz = styles.actionButton(context).name) {
        safeBlankHref = destinationEndpoint
        +label
        +arrowRightThinIcon(clazz = styles.actionButtonArrow.name)
    }



// Styles.

private class NotificationMessageStyles(
    private val materialDesignTokens: MaterialDesignTokens
) : DynamicStyleSheet(materialDesignTokens::class) {

    val container: DynamicCssProvider<Context> by dynamicCss {
        display = Display.flex
        backgroundColor = materialDesignTokens.system.palette.backSpecial(it)
    }

    val titleAndDescriptionWrapper: NamedRuleSet by css { flexGrow = 1.0 }

    val title: DynamicCssProvider<Context> by dynamicCss {
        +materialDesignTokens.system.fontStyles.bold(it).rules
        padding = Padding(
            top   = materialDesignTokens.reference.spacing.absolute25,
            left  = materialDesignTokens.reference.spacing.absolute40,
            right = materialDesignTokens.reference.spacing.absolute40,
        )
        fontSize = materialDesignTokens.reference.fontSizes.relative1p2
        color = materialDesignTokens.system.palette.onBackSpecial(it)
    }

    val description: DynamicCssProvider<Context> by dynamicCss {
        padding = Padding(
            top        = materialDesignTokens.reference.spacing.absolute8,
            horizontal = materialDesignTokens.reference.spacing.absolute40,
            bottom     = materialDesignTokens.reference.spacing.absolute26,
        )
        fontSize = materialDesignTokens.reference.fontSizes.relative0p85
        color = materialDesignTokens.system.palette.onBackSpecial(it)
    }

    val actionButton: DynamicCssProvider<Context> by dynamicCss {
        display    = Display.flex
        alignItems = Align.center
        alignSelf  = Align.center
        flexShrink = 0
        padding = Padding(
            top    = materialDesignTokens.reference.spacing.absolute11p5,
            bottom = materialDesignTokens.reference.spacing.absolute11p5,
            left   = materialDesignTokens.reference.spacing.absolute15,
            right  = materialDesignTokens.reference.spacing.absolute18,
        )
        marginRight = materialDesignTokens.reference.spacing.absolute40
        borderRadius = materialDesignTokens.reference.radii.cornerAbsolute24
        fontSize       = materialDesignTokens.reference.fontSizes.relative0p9
        textDecoration = TextDecoration.none
        color           = materialDesignTokens.system.palette.onAction1(it)
        backgroundColor = materialDesignTokens.system.palette.action1(it)
        hover {
            backgroundColor = materialDesignTokens.system.palette.action1Focused(it)
        }
    }

    val actionButtonArrow: NamedRuleSet by css {
        marginLeft = materialDesignTokens.reference.spacing.absolute12
    }

}
