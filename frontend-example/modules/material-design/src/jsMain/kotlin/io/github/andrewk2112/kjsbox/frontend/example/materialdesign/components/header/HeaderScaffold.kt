package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.header

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.resources.endpoints.MainMaterialEndpoints
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.learnAboutMaterial3sNewFeaturesKey
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.startExploringMaterial3Key
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.theLatestVersionOfMaterialDesignIsNowAvailableForAndroidKey
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useLocalizator
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context.ScreenSize.PHONE
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.useDesignTokensContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.header.sections.Header
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.header.sections.NotificationMessage
import kotlinx.css.Display
import kotlinx.css.display
import react.ChildrenBuilder
import react.FC
import react.dom.html.ReactHTML.div



// Components.

val HeaderScaffold = FC<HeaderProps> { props ->

    val context     = useDesignTokensContext()
    val localizator = useLocalizator()

    NotificationVisibility(context) {
        NotificationMessage(
            context,
            localizator(theLatestVersionOfMaterialDesignIsNowAvailableForAndroidKey),
            localizator(learnAboutMaterial3sNewFeaturesKey),
            localizator(startExploringMaterial3Key),
            MainMaterialEndpoints.DESIGN
        )
    }
    Header {
        hasCloseableMenu = props.hasCloseableMenu
        onMenuToggle     = props.onMenuToggle
    }

}

@Suppress("FunctionName")
private inline fun ChildrenBuilder.NotificationVisibility(
    context: Context,
    crossinline children: ChildrenBuilder.() -> Unit
) =
    +div(clazz = HeaderScaffoldStyles.notificationVisibility(context.screenSize > PHONE).name, children)



// Styles.

private object HeaderScaffoldStyles : DynamicStyleSheet() {

    val notificationVisibility: DynamicCssProvider<Boolean> by dynamicCss {
        if (!it) {
            display = Display.none
        }
    }

}
