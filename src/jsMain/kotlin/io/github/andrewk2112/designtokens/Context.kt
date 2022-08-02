package io.github.andrewk2112.designtokens

import io.github.andrewk2112.designtokens.stylesheets.HasCssSuffix
import io.github.andrewk2112.extensions.camelCaseWord

/**
 * Describes the current UI state to invalidate it accordingly - something like extended media queries.
 * */
data class Context(val screenSize: ScreenSize, val colorMode: ColorMode) : HasCssSuffix {

    // Utility.

    /**
     * Supported screen sizes.
     *
     * @param startsFromWidth A minimal width of the concrete [ScreenSize].
     * */
    enum class ScreenSize(private val startsFromWidth: Int) {

        // Keep the same order of the declarations here as it can harm values' retrievals.
        DESKTOP(992),
        TABLET(768),
        PHONE(0);

        companion object {

            /**
             * Static retrieval of the concrete [ScreenSize] matching to a given [rawWidth].
             * */
            fun fromRawWidth(rawWidth: Int): ScreenSize {
                for (value in values()) {
                    if (rawWidth >= value.startsFromWidth) return value
                }
                return DESKTOP
            }

        }

    }

    /**
     * Supported color modes.
     * */
    enum class ColorMode { LIGHT, DARK }



    // Implementations.

    override val cssSuffix = "${screenSize.name.lowercase()}${colorMode.name.camelCaseWord()}"

}
