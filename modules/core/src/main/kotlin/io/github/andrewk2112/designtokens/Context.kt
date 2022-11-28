package io.github.andrewk2112.designtokens

import io.github.andrewk2112.stylesheets.HasCssSuffix
import io.github.andrewk2112.extensions.camelCaseWord
import io.github.andrewk2112.extensions.lowerCamelCaseFromKebabOrSnakeCase

/**
 * Describes the current UI state to invalidate it accordingly - something like extended media queries.
 */
data class Context(val screenSize: ScreenSize, val colorMode: ColorMode) : HasCssSuffix {

    // Utility.

    /**
     * Supported screen sizes.
     *
     * @param startsFromWidth A minimal width of the concrete [ScreenSize].
     */
    enum class ScreenSize(private val startsFromWidth: Int) {

        // Keep the same order of the declarations here as it can harm the values' retrievals.
        DESKTOP(1340),
        BIG_TABLET(920),
        SMALL_TABLET(520),
        PHONE(0);

        companion object {

            /**
             * Static retrieval of the concrete [ScreenSize] matching to a given [rawWidth].
             */
            fun fromRawWidth(rawWidth: Int): ScreenSize {
                for (value in values()) {
                    if (rawWidth >= value.startsFromWidth) return value
                }
                return DESKTOP
            }

        }

        /**
         * Checks whether the current [ScreenSize] is equals or bigger than the provided [screenSize].
         */
        fun equalsOrBigger(screenSize: ScreenSize): Boolean = startsFromWidth >= screenSize.startsFromWidth

        /**
         * Checks whether the current [ScreenSize] is equals or smaller than the provided [screenSize].
         */
        fun equalsOrSmaller(screenSize: ScreenSize): Boolean = startsFromWidth <= screenSize.startsFromWidth

    }

    /**
     * Supported color modes.
     */
    enum class ColorMode { LIGHT, DARK }



    // Implementations.

    override val cssSuffix = "${screenSize.name.lowerCamelCaseFromKebabOrSnakeCase()}${colorMode.name.camelCaseWord()}"

}
