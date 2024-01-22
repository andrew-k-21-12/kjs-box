package io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection

import io.github.andrewk2112.kjsbox.frontend.core.localization.LocalizationEngine
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.modules.designTokensModule
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.modules.localizationModule
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.DesignTokens
import io.github.andrewk2112.utility.kodein.KodeinDirectInjection

/**
 * The app's main dependency injection component containing dependencies used everywhere - even in lazy modules.
 *
 * This component and its related modules can be substituted by different Gradle modules
 * (for test builds or other variants) - just keep the name and package the same.
 */
class RootComponent {

    fun getDesignTokens(): DesignTokens = kodeinInjection()
    fun getLocalizationEngine(): LocalizationEngine = kodeinInjection()

    private val kodeinInjection = KodeinDirectInjection(
        designTokensModule,
        localizationModule,
    )

}
