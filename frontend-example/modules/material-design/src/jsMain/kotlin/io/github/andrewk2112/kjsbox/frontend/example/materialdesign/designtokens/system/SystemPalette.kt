package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system

import io.github.andrewk2112.kjsbox.frontend.designtokens.ContextedPalette
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.reference.ReferencePalette

class SystemPalette(private val palette: ReferencePalette) : ContextedPalette<Context>() {

    val action1        = get { palette.blue2 }
    val action1Focused = get { palette.blue3 }
    val onAction1      = get { palette.blueDark2 }

    val action2 = get { palette.black }
    val action3 = get { palette.gray10 }

    val backSpecial   = get { palette.blue1 }
    val onBackSpecial = get { palette.blueDark1 }

    val scrim = get { palette.blackAlpha4 }

    val selection1Focused = get { palette.gray2 }
    val selection1Active  = get { palette.gray3 }
    val onSelection1      = get { palette.gray10 }

    val selection2Focused = get { palette.gray9 }
    val selection2Active  = get { palette.gray7 }

    val stroke1 = get { palette.blackAlpha1 }
    val stroke2 = get { palette.gray5Alpha }

    val surface1          = get { palette.gray10 }
    val onSurface1        = get { palette.white }
    val onSurface1Weaker  = get { palette.gray2 }
    val onSurface1Focused = get { palette.gray4 }
    val onSurface1Dimmed  = get { palette.gray5 }

    val surface2                  = get { palette.white }
    val onSurface2                = get { palette.gray10 }
    val onSurface2SlightlyLighter = get { palette.gray8 }
    val onSurface2Lighter         = get { palette.gray6 }

    val surface3   = get { palette.gray1 }
    val onSurface3 = get { palette.gray8 }

}
