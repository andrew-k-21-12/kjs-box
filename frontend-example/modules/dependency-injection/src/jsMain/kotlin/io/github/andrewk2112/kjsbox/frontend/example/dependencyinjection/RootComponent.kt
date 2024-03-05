package io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection

import io.github.andrewk2112.kjsbox.frontend.example.designtokens.DesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.localization.LocalizationEngine

/**
 * The app's main dependency injection component: provides dependencies used everywhere - even in lazy modules.
 */
interface RootComponent {
    fun getDesignTokens(): DesignTokens
    fun getLocalizationEngine(): LocalizationEngine
}
