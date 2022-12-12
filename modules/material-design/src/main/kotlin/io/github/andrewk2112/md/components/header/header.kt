package io.github.andrewk2112.md.components.header

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.md.resources.endpoints.MainMaterialEndpoints
import react.*

val header = FC<HeaderProps> { props ->

    val context     = useAppContext()
    val localizator = useLocalizator()

     headerNotification(
         context,
         isVisible = context.screenSize.equalsOrBigger(Context.ScreenSize.SMALL_TABLET),
         localizator("md.theLatestVersionOfMaterialDesignIsNowAvailableForAndroid"),
         localizator("md.learnAboutMaterial3sNewFeatures"),
         localizator("md.startExploringMaterial3"),
         MainMaterialEndpoints.design
     )

    headerNavigation { onMenuClick = props.onMenuClick }

}
