package io.github.andrewk2112.designtokens

/**
 * Describes the current UI state to invalidate it accordingly - something like extended media queries.
 * */
data class Context(val screenSize: ScreenSize, val colorMode: ColorMode) {

    /**
     * Supported screen sizes.
     *
     * @param startsFromWidth A minimal width of the concrete [ScreenSize].
     * */
    enum class ScreenSize(private val startsFromWidth: Int) {

        PHONE(0),
        TABLET(768),
        DESKTOP(992);

        companion object {

            /**
             * Static construction of a concrete [ScreenSize] from a [rawWidth].
             * */
            fun fromRawWidth(rawWidth: Int): ScreenSize = // it might be reasonable to avoid allocations here
                values()                                  // if performance issues appear
                    .sortedByDescending { it.startsFromWidth }
                    .firstOrNull { rawWidth >= it.startsFromWidth } ?: PHONE

        }

    }

    /**
     * Supported color modes.
     * */
    enum class ColorMode { LIGHT, DARK }

}
