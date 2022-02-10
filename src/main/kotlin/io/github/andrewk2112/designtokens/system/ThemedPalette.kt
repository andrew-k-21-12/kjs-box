package io.github.andrewk2112.designtokens.system

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.reference.Palette
import kotlinx.css.Color

/**
 * Provides context-based colors according to the current dynamic configuration.
 * */
object ThemedPalette {

    // Public.

    /**
     * A protocol to describe dynamic themed colors.
     * */
    interface ThemedColor {

        /**
         * Returns some specific [Color] according to the provided [context].
         * */
        fun get(context: Context): Color

    }

    val background1 get() = themedColor { Palette.white }



    // Private.

    /**
     * A helper factory to create [ThemedColor]s.
     *
     * @param builder Describes how to get the exact color according to the current [Context].
     *
     * @return A created instance of the [ThemedColor].
     * */
    private inline fun themedColor(crossinline builder: (Context) -> Color) = object : ThemedColor {
        override fun get(context: Context): Color = builder.invoke(context)
    }

}
