package io.github.andrewk2112.ui.containers.md

import io.github.andrewk2112.ui.components.md.notificationHeader
import io.github.andrewk2112.designtokens.StyleValues
import io.github.andrewk2112.designtokens.stylesheets.DynamicStyleSheet
import io.github.andrewk2112.designtokens.stylesheets.NamedRuleSet
import io.github.andrewk2112.hooks.useAppContext
import io.github.andrewk2112.hooks.useLocalizator
import io.github.andrewk2112.ui.styles.ShadowStyles
import kotlinx.css.fontSize
import kotlinx.css.pct
import react.Props
import react.dom.div
import react.fc

// TODO:
//  0. Suggest styles wrapping to avoid direct usage of kotlin-styled-next (another proposal and PR)!

// TODO:
//  1. Reply to SO on Linked vs ArrayList, save this and other SO articles somewhere!
//  2. Dependencies on inner variables are not good (in components).
//     Also, it can be reasonable to avoid lots of singletons (e.g., for stateless views) which always live in the memory.

// TODO:
//  3. Hashes in names for all resources (fonts, locales, images) are not needed,
//     as it will require to rebuild and reload everything each time a resource changes.
//  4. Fix webpack warnings and do a clean-up for its scripts.
//  5. Write some custom server with all required configs (caches, routing) and place it here.
//  6. Extract a module with a framework itself.



// Public.

val materialDesign = fc<Props> {

    val context     = useAppContext()
    val localizator = useLocalizator()

    div(MaterialDesignStyles.root.name) {

        div(ShadowStyles.defaultShadow.name) {

            notificationHeader(
                context,
                localizator("md.theLatestVersionOfMaterialDesignIsNowAvailableForAndroid"),
                localizator("md.learnAboutMaterial3sNewFeatures"),
                localizator("md.startExploringMaterial3"),
                ENDPOINT_TO_MATERIAL_3
            )

            navigationHeader {}

        }

    }

}



// Private.

private object MaterialDesignStyles : DynamicStyleSheet() {

    val root: NamedRuleSet by css {
        +StyleValues.fontFaces.roboto.rules
        fontSize = 100.pct
    }

}

private const val ENDPOINT_TO_MATERIAL_3 = "https://material.io/design"
