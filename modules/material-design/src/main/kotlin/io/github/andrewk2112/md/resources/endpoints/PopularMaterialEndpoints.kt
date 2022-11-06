package io.github.andrewk2112.md.resources.endpoints

import io.github.andrewk2112.md.resources.endpoints.MainMaterialEndpoints.design
import io.github.andrewk2112.md.resources.endpoints.MainMaterialEndpoints.root

/**
 * All popular Material articles endpoints.
 * */
class PopularMaterialEndpoints {
    val materialTheming = "$design/material-theming"
    val iconography     = "$design/iconography"
    val textFields      = "$root/components/text-fields"
}
