package io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.kodein.modules

import io.github.andrewk2112.kjsbox.frontend.example.designtokens.DesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.ReferenceDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.SystemDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.reference.*
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.system.SystemFontSizes
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.system.SystemPalette
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

/** Provides all design tokens. */
internal val designTokensModule inline get() = DI.Module("DesignTokens") {

    // Reference.
    bindSingleton { ReferenceFontSizes() }
    bindSingleton { ReferencePalette() }
    bindSingleton { ReferenceSpacing() }
    bindSingleton { ReferenceDesignTokens(instance(), instance(), instance()) }

    // System.
    bindSingleton { SystemFontSizes(instance()) }
    bindSingleton { SystemPalette(instance()) }
    bindSingleton { SystemDesignTokens(instance(), instance()) }

    // All together.
    bindSingleton { DesignTokens(instance(), instance()) }

}
