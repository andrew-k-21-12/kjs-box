package io.github.andrewk2112.md.components.header

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.Context.ScreenSize.PHONE
import io.github.andrewk2112.extensions.invoke
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.md.components.header.sections.header
import io.github.andrewk2112.md.components.header.sections.notificationMessage
import io.github.andrewk2112.md.resources.endpoints.MainMaterialEndpoints
import io.github.andrewk2112.stylesheets.DynamicCssProvider
import io.github.andrewk2112.stylesheets.DynamicStyleSheet
import kotlinx.css.Display
import kotlinx.css.display
import react.*
import react.dom.html.ReactHTML.div



// Components.

val headerScaffold = FC<HeaderProps> { props ->

    val context     = useAppContext()
    val localizator = useLocalizator()

    notificationVisibility(context) {
        notificationMessage(
            context,
            localizator("md.theLatestVersionOfMaterialDesignIsNowAvailableForAndroid"),
            localizator("md.learnAboutMaterial3sNewFeatures"),
            localizator("md.startExploringMaterial3"),
            MainMaterialEndpoints.design
        )
    }
    header {
        hasCloseableMenu = props.hasCloseableMenu
        onMenuToggle     = props.onMenuToggle
    }

}

private inline fun ChildrenBuilder.notificationVisibility(
    context: Context,
    crossinline children: ChildrenBuilder.() -> Unit
) =
    +div(HeaderScaffoldStyles.notificationVisibility(context.screenSize > PHONE).name, block = children)



// Styles.

private object HeaderScaffoldStyles : DynamicStyleSheet() {

    val notificationVisibility: DynamicCssProvider<Boolean> by dynamicCss {
        if (!it) {
            display = Display.none
        }
    }

}
