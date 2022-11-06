package io.github.andrewk2112.md.resources.endpoints

import io.github.andrewk2112.md.resources.endpoints.MainMaterialEndpoints.blog
import io.github.andrewk2112.md.resources.endpoints.MainMaterialEndpoints.root

/**
 * Material endpoints to the recent articles.
 * */
class WhatsNewMaterialEndpoints {
    val whatsNew               = "$root/whats-new"
    val largeScreens           = "$blog/material-design-for-large-screens"
    val combinedComponentPages = "$root/components"
    val mdaWinners             = "$blog/mda-2020-winners"
}
