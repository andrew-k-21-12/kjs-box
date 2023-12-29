package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection

import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.modules.designTokensModule
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import io.github.andrewk2112.utility.kodein.KodeinDirectInjection

/**
 * Material design dependency injection component containing related dependencies.
 *
 * This component and its related modules can be substituted by different Gradle modules
 * (for test builds or other variants) - just keep the name and package the same.
 */
class MaterialDesignComponent {

    fun getMaterialDesignTokens(): MaterialDesignTokens = kodeinInjection()

    private val kodeinInjection = KodeinDirectInjection(designTokensModule)

}
