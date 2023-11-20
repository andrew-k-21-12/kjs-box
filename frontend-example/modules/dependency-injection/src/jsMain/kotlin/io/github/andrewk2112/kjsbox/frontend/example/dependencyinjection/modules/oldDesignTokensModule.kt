package io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.modules

import io.github.andrewk2112.kjsbox.frontend.example.designtokens.ReferenceDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.SystemDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.reference.*
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.system.SystemFontSizes
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.system.SystemPalette
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

/** Provides all design tokens. */
internal val designTokensModule = DI.Module("DesignTokens") {
    bindSingleton { ReferenceFontSizes() }
    bindSingleton { ReferencePalette() }
    bindSingleton {
        ReferenceDesignTokens(
            instance(),
            instance(),
            ReferenceSpacing(),
        )
    }
    bindSingleton {
        SystemDesignTokens(
            SystemFontSizes(instance()),
            SystemPalette(instance()),
        )
    }
}
