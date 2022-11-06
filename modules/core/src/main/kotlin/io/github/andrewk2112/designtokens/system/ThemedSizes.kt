package io.github.andrewk2112.designtokens.system

import io.github.andrewk2112.designtokens.StyleValues

/**
 * Provides context-based sizes according to the current dynamic configuration.
 * */
class ThemedSizes {
    val stroke1: ThemedSize get() = { StyleValues.sizes.absolute1 }
}
