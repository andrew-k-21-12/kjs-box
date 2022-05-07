package io.github.andrewk2112.components.md

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.designtokens.stylesheets.DynamicCssProvider
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.jsmodules.svg.SvgFile
import io.github.andrewk2112.jsmodules.svg.invoke
import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.Props
import react.dom.attrs
import react.dom.html.AnchorTarget
import react.fc
import styled.*

// Public.

external interface NotificationHeaderProps : Props {
    var title: String
    var description: String
    var actionLabel: String
    var actionDestination: String
}

val notificationHeader = fc<NotificationHeaderProps> { props ->

    val context = useAppContext()

    styledDiv {

        css(NotificationHeaderStyles.container(context))

        // Title and description rows.
        styledDiv {

            css(NotificationHeaderStyles.titleAndDescriptionRows)

            // Title.
            styledP {
                css(NotificationHeaderStyles.title(context))
                +props.title
            }

            // Description.
            styledP {
                css(NotificationHeaderStyles.description(context))
                +props.description
            }

        }

        // Action button.
        styledA {
            css(NotificationHeaderStyles.actionButton(context))
            attrs {
                target = AnchorTarget._blank.toString()
                href   = props.actionDestination
            }
            +props.actionLabel



            // FIXME:
            //  1. Provide my DynamicStyleSheet to GitHub.
            //  2. Avoid direct usage of kotlin-styled-next at all - create wrappers to add styles to DOM elements.
            //  3. Complete SvgFile and extend my SO answer!
            +arrowRightThin()
            styled(arrowRightThin.component).invoke(this) {
                css {
                    marginLeft = 20.px
                }
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

    val titleAndDescriptionRows: RuleSet by css { flexGrow = 1.0 }

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
        paddingRight  = StyleValues.spacing.absolute15
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

}

@JsModule("./icons/arrow-right-thin.svg")
@JsNonModule
private external val arrowRightThin: SvgFile
