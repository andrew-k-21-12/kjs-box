package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.header

import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.extensions.invoke
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.resources.endpoints.MainMaterialEndpoints
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicCssProvider
import io.github.andrewk2112.kjsbox.frontend.dynamicstylesheet.DynamicStyleSheet
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.hooks.useLocalizator
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context.ScreenSize.PHONE
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.useDesignTokensContext
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.header.sections.Header
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.components.header.sections.NotificationMessage
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.LEARN_ABOUT_MATERIAL3S_NEW_FEATURES_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.START_EXPLORING_MATERIAL3_KEY
import io.github.andrewk2112.kjsbox.frontend.example.resourcewrappers.locales.materialdesign.TranslationLocalizationKeys.THE_LATEST_VERSION_OF_MATERIAL_DESIGN_IS_NOW_AVAILABLE_FOR_ANDROID_KEY
import io.github.andrewk2112.utility.react.components.FC
import kotlinx.css.Display
import kotlinx.css.display
import react.ChildrenBuilder
import react.dom.html.ReactHTML.div



// Components.

val HeaderScaffold by FC<HeaderProps> { props ->

    val context     = useDesignTokensContext()
    val localizator = useLocalizator()

    NotificationVisibility(context) {
        NotificationMessage(
            context,
            localizator(THE_LATEST_VERSION_OF_MATERIAL_DESIGN_IS_NOW_AVAILABLE_FOR_ANDROID_KEY),
            localizator(LEARN_ABOUT_MATERIAL3S_NEW_FEATURES_KEY),
            localizator(START_EXPLORING_MATERIAL3_KEY),
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
