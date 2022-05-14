package io.github.andrewk2112.components.md

import csstype.ClassName
import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.designtokens.stylesheets.DynamicCssProvider
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.designtokens.stylesheets.NamedRuleSet
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.jsmodules.svg.SvgFile
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.Props
import react.dom.a
import react.dom.attrs
import react.dom.div
import react.dom.html.AnchorTarget
import react.dom.p
import react.fc

// Public.

external interface NotificationHeaderProps : Props {
    var title: String
    var description: String
    var actionLabel: String
    var actionDestination: String
}

val notificationHeader = fc<NotificationHeaderProps> { props ->

    val context = useAppContext()

    div(NotificationHeaderStyles.container(context).name) {

        // Title and description rows.
        div(NotificationHeaderStyles.titleAndDescriptionRows.name) {

            // Title.
            p(NotificationHeaderStyles.title(context).name) {
                +props.title
            }

            // Description.
            p(NotificationHeaderStyles.description(context).name) {
                +props.description
            }

        }

        // Action button.
        a(classes = NotificationHeaderStyles.actionButton(context).name) {

            attrs {
                target = AnchorTarget._blank.toString()
                href   = props.actionDestination
            }

            +props.actionLabel

            arrowRightThin.component {
                attrs.className = ClassName(NotificationHeaderStyles.actionButtonArrow.name)
            }

        }

    }

}



// Private.

private object NotificationHeaderStyles : DynamicStyleSheet() {

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
        paddingTop    = StyleValues.spacing.absolute12
        paddingBottom = StyleValues.spacing.absolute12
        paddingLeft   = StyleValues.spacing.absolute15
        paddingRight  = StyleValues.spacing.absolute18
        marginRight   = StyleValues.spacing.absolute40
        borderRadius  = StyleValues.cornerRadii.absolute24
        fontSize       = StyleValues.fontSizes.relativep90
        textDecoration = TextDecoration.none
        backgroundColor = Theme.palette.action2(it)
        hover {
            backgroundColor = Theme.palette.actionFocused2(it)
        }
        color = Theme.palette.onAction2(it)
    }

    val actionButtonArrow by css {
        marginLeft = StyleValues.spacing.absolute12
    }

}

@JsModule("./icons/arrow-right-thin.svg")
@JsNonModule
private external val arrowRightThin: SvgFile
