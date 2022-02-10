package io.github.andrewk2112.designtokens.system

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.reference.FontSizes
import kotlinx.css.LinearDimension

/**
 * Provides context-based font sizes according to the current dynamic configuration.
 * */
object ThemedFontSizes {

    // Public.

    /**
     * A protocol to describe dynamic themed font sizes.
     * */
    interface ThemedFontSize {

        /**
         * Returns some specific font size according to the provided [context].
         * */
        fun get(context: Context): LinearDimension

    }

    val label1 get() = themedFontSize { FontSizes.relative2 }



    // Private.

    /**
     * A helper factory to create [ThemedFontSize]s.
     *
     * @param builder Describes how to get the exact font size according to the current [Context].
     *
     * @return A created instance of the [ThemedFontSize].
     * */
    private inline fun themedFontSize(crossinline builder: (Context) -> LinearDimension) = object : ThemedFontSize {
        override fun get(context: Context): LinearDimension = builder.invoke(context)
    }

}
