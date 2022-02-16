package io.github.andrewk2112.designtokens.system

import io.github.andrewk2112.designtokens.Context
import io.github.andrewk2112.designtokens.StyleValues
import kotlinx.css.RuleSet

/**
 * Provides context-based font faces according to the current dynamic configuration.
 * */
class ThemedFontFaces {

    // Public.

    /**
     * A protocol to describe dynamic themed font faces.
     * */
    interface ThemedFontFace {

        /**
         * Returns some specific font face according to the provided [context].
         * */
        fun get(context: Context): RuleSet

    }

    val main get() = themedFontFace { StyleValues.fontFaces.sourceSansPro }
    val accent get() = themedFontFace { StyleValues.fontFaces.comfortaa }



    // Private.

    /**
     * A helper factory to create [ThemedFontFace]s.
     *
     * @param builder Describes how to get the exact font face according to the current [Context].
     *
     * @return A created instance of the [ThemedFontFace].
     * */
    private inline fun themedFontFace(crossinline builder: (Context) -> RuleSet) = object : ThemedFontFace {
        override fun get(context: Context): RuleSet = builder.invoke(context)
    }

}
