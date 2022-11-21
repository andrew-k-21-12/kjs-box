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

    val action3: ThemedColor get() = { StyleValues.palette.black }

    val action4: ThemedColor get() = { StyleValues.palette.gray10 }

    val backSpecial1:   ThemedColor get() = { StyleValues.palette.blue1 }
    val onBackSpecial1: ThemedColor get() = { StyleValues.palette.blueDark1 }

    val selectionFocused1: ThemedColor get() = { StyleValues.palette.gray2 }
    val selectionActive1:  ThemedColor get() = { StyleValues.palette.gray3 }
    val onSelection1:      ThemedColor get() = { StyleValues.palette.gray10 }

    val selectionFocused2: ThemedColor get() = { StyleValues.palette.gray9 }
    val selectionActive2:  ThemedColor get() = { StyleValues.palette.gray7 }

    val surface1:          ThemedColor get() = { StyleValues.palette.gray10 }
    val onSurface1:        ThemedColor get() = { StyleValues.palette.white }
    val onSurfaceWeaker1:  ThemedColor get() = { StyleValues.palette.gray2 }
    val onSurfaceFocused1: ThemedColor get() = { StyleValues.palette.gray4 }
    val onSurfaceDimmed1:  ThemedColor get() = { StyleValues.palette.gray5 }

    val surface2:                  ThemedColor get() = { StyleValues.palette.white }
    val onSurface2:                ThemedColor get() = { StyleValues.palette.gray10 }
    val onSurfaceSlightlyLighter2: ThemedColor get() = { StyleValues.palette.gray8 }
    val onSurfaceLighter2:         ThemedColor get() = { StyleValues.palette.gray6 }

    val surface3:   ThemedColor get() = { StyleValues.palette.gray1 }
    val onSurface3: ThemedColor get() = { StyleValues.palette.gray8 }

    val stroke1: ThemedColor get() = { StyleValues.palette.blackWithAlpha1 }
    val stroke2: ThemedColor get() = { StyleValues.palette.gray5WithAlpha }

}
