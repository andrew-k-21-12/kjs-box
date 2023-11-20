package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.modules

import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.ReferenceDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.SystemDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.reference.*
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system.SystemFontSizes
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system.SystemPalette
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system.SystemSizes
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

/** Provides material design tokens. */
val designTokensModule = DI.Module("DesignTokens") {
    bindSingleton { ReferenceFontSizes() }
    bindSingleton { ReferencePalette() }
    bindSingleton { ReferenceSizes() }
    bindSingleton {
        ReferenceDesignTokens(
            instance(),
            ReferenceOpacities(),
            instance(),
            ReferenceRadii(),
            instance(),
            ReferenceSpacing(),
            ReferenceTime(),
            ReferenceTiming(),
        )
    }
    bindSingleton {
        SystemDesignTokens(
            SystemFontSizes(instance()),
            SystemPalette(instance()),
            SystemSizes(instance()),
        )
    }
}
