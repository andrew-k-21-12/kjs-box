package io.github.andrewk2112.designtokens

/**
 * Describes the current UI state to invalidate it accordingly - something like extended media queries.
 * */
data class Context(val screenSize: ScreenSize, val colorMode: ColorMode) {

    /**
     * Supported screen sizes.
     * */
    enum class ScreenSize { DESKTOP, TABLET, PHONE }

    /**
     * Supported color modes.
     * */
    enum class ColorMode { LIGHT, DARK }

}
