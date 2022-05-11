package io.github.andrewk2112.containers.md

import io.github.andrewk2112.components.md.notificationHeader
import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.Theme
import io.github.andrewk2112.designtokens.stylesheets.DynamicCssProvider
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.localization.localization
import kotlinx.css.fontSize
import kotlinx.css.pct
import react.Props
import react.dom.div
import react.fc

// TODO:
//  1. Reply to SO on Linked vs ArrayList, save this and other SO articles somewhere!
//  2. Maybe it's better to drop the intermediate variable for the localization and use an effect function directly.
//  3. Dependencies on inner variables are not good (in components).

// TODO:
//  4. Hashes in names for all resources (fonts, locales, images) are not needed,
//     as it will require to rebuild and reload everything each time a resource changes.
//  5. Fix webpack warnings and do a clean-up for its scripts.
//  6. Write some custom server with all required configs (caches, routing) and place it here.
//  7. Extract a module with a framework itself.



// Public.

val materialDesign = fc<Props> {

    val context     = useAppContext()
    val localizator = localization.useLocalizator()

    div(MaterialDesignStyles.root(context).name) {

        notificationHeader {
            attrs {
                title             = localizator("md.theLatestVersionOfMaterialDesignIsNowAvailableForAndroid")
                description       = localizator("md.learnAboutMaterial3sNewFeatures")
                actionLabel       = localizator("md.startExploringMaterial3")
                actionDestination = ENDPOINT_TO_MATERIAL_3
            }
        }

    }

}



// Private.

private object MaterialDesignStyles : DynamicStyleSheet() {

    val root: DynamicCssProvider<Context> by dynamicCss {
        +Theme.fontFaces.material(it).rules
        fontSize = 100.pct
    }

}

private const val ENDPOINT_TO_MATERIAL_3 = "https://m3.material.io"
