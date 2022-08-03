package io.github.andrewk2112.designtokens.reference

import kotlinx.css.Color

/**
 * All source colors - to be used in derived design tokens and styles.
 * */
class Palette {

    // The order of tones is from lighter to darker.

    val blackWithAlpha1 get() = Color("#0000001f")
    val blackWithAlpha2 get() = Color("#00000080")

    val gray1          get() = Color("#F5F5F5")
    val gray2          get() = Color("#D7D7D7")
    val gray3          get() = Color("#D3D3D3")
    val gray4          get() = Color("#9E9E9E")
    val gray4WithAlpha get() = Color("#9E9E9E4C")
    val gray5          get() = Color("#616161")
    val gray6          get() = Color("#212121")

    val white get() = Color("#FFFFFF")

    val blue1 get() = Color("#E9F0F6")
    val blue2 get() = Color("#CBE6FD")
    val blue3 get() = Color("#BAD6ED")
    val blue4 get() = Color("#38D4FF")
    val blue5 get() = Color("#75B5C7")

    val blueDark1 get() = Color("#2A638A")
    val blueDark2 get() = Color("#081D2D")

}
