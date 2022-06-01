package io.github.andrewk2112.designtokens.system

import io.github.andrewk2112.designtokens.StyleValues

/**
 * Provides context-based colors according to the current dynamic configuration.
 * */
class ThemedPalette {

    val action1:       ThemedColor get() = { StyleValues.palette.blue4 }
    val actionDimmed1: ThemedColor get() = { StyleValues.palette.blue5 }

    val action2:        ThemedColor get() = { StyleValues.palette.blue2 }
    val actionFocused2: ThemedColor get() = { StyleValues.palette.blue3 }
    val onAction2:      ThemedColor get() = { StyleValues.palette.blueDark2 }

    val backSpecial1:   ThemedColor get() = { StyleValues.palette.blue1 }
    val onBackSpecial1: ThemedColor get() = { StyleValues.palette.blueDark1 }

    val surface1:          ThemedColor get() = { StyleValues.palette.gray3 }
    val onSurface1:        ThemedColor get() = { StyleValues.palette.white }
    val onSurfaceFocused1: ThemedColor get() = { StyleValues.palette.gray1 }
    val onSurfaceDimmed1:  ThemedColor get() = { StyleValues.palette.gray2 }

    val surface2:   ThemedColor get() = { StyleValues.palette.white }
    val onSurface2: ThemedColor get() = { StyleValues.palette.gray3 }

    val stroke: ThemedColor get() = { StyleValues.palette.blackWithAlpha1 }

}
