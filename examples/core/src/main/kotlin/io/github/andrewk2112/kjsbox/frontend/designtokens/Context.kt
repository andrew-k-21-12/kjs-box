package io.github.andrewk2112.kjsbox.frontend.designtokens

import io.github.andrewk2112.kjsbox.frontend.stylesheets.HasCssSuffix
import io.github.andrewk2112.kjsbox.frontend.extensions.camelCaseWord
import io.github.andrewk2112.kjsbox.frontend.extensions.lowerCamelCaseFromKebabOrSnakeCase

/**
 * Describes the current UI state to invalidate it accordingly - something like extended media queries.
 */
data class Context(val screenSize: ScreenSize, val colorMode: ColorMode) : HasCssSuffix {

    // Utility.

    /**
     * Supported screen sizes.
     *
     * @param maxWidth The maximal width of the concrete [ScreenSize].
     */
    enum class ScreenSize(private val maxWidth: Int) {

        // Keep the same order of the declarations here as it can harm the values' retrievals and comparisons.
        PHONE(520),
        SMALL_TABLET(920),
        BIG_TABLET(1340),
        DESKTOP(Int.MAX_VALUE);

        companion object {

            /**
             * Static retrieval of the concrete [ScreenSize] matching to a given [rawWidth].
             */
            fun fromRawWidth(rawWidth: Int): ScreenSize {
                for (value in values()) {
                    if (rawWidth <= value.maxWidth) return value
                }
                return DESKTOP
            }

        }

    }

    /**
     * Supported color modes.
     */
    enum class ColorMode { LIGHT, DARK }



    // Implementations.

    override val cssSuffix = "${screenSize.name.lowerCamelCaseFromKebabOrSnakeCase()}${colorMode.name.camelCaseWord()}"

}
