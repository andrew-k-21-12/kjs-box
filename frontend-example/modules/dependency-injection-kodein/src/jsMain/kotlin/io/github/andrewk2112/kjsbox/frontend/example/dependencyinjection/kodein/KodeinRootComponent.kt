package io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.kodein

import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.RootComponent
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.kodein.modules.coroutinesModule
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.kodein.modules.designTokensModule
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.kodein.modules.localizationModule
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.DesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.localization.LocalizationEngine
import io.github.andrewk2112.utility.kodein.KodeinDirectInjection

/**
 * **Kodein**-backed implementation of the [RootComponent].
 */
class KodeinRootComponent : RootComponent {

    override fun getDesignTokens(): DesignTokens = kodeinInjection()
    override fun getLocalizationEngine(): LocalizationEngine = kodeinInjection()

    private val kodeinInjection = KodeinDirectInjection(
        coroutinesModule,
        designTokensModule,
        localizationModule,
    )

}
