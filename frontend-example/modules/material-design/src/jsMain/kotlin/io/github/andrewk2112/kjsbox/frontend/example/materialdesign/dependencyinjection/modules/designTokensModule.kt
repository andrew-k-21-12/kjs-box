package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.modules

import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.ComponentDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.ReferenceDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.SystemDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.component.*
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.reference.*
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system.SystemFontSizes
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system.SystemFontStyles
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system.SystemPalette
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system.SystemSizes
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

/** Provides material design tokens. */
val designTokensModule inline get() = DI.Module("DesignTokens") {

    // Reference.
    bindSingleton { ReferenceFontSizes() }
    bindSingleton { ReferenceOpacities() }
    bindSingleton { ReferencePalette() }
    bindSingleton { ReferenceRadii() }
    bindSingleton { ReferenceSizes() }
    bindSingleton { ReferenceSpacing() }
    bindSingleton { ReferenceTime() }
    bindSingleton { ReferenceTiming() }
    bindSingleton {
        ReferenceDesignTokens(
            instance(), instance(), instance(), instance(), instance(), instance(), instance(), instance(),
        )
    }

    // System.
    bindSingleton { SystemFontSizes(instance(), instance()) }
    bindSingleton { SystemFontStyles() }
    bindSingleton { SystemPalette(instance()) }
    bindSingleton { SystemSizes(instance()) }
    bindSingleton { SystemDesignTokens(instance(), instance(), instance(), instance()) }

    // Component.
    bindSingleton { MaterialDesignComponentImageStyles(instance()) }
    bindSingleton { MaterialDesignComponentLabelStyles(instance(), instance(), instance(), instance(), instance()) }
    bindSingleton { MaterialDesignComponentLayoutStyles(instance(), instance()) }
    bindSingleton { MaterialDesignComponentSelectionStyles(instance(), instance(), instance(), instance(), instance()) }
    bindSingleton { MaterialDesignComponentShadowStyles(instance(), instance(), instance()) }
    bindSingleton { MaterialDesignComponentStrokeStyles(instance(), instance()) }
    bindSingleton { MaterialDesignComponentTransitionStyles(instance(), instance()) }
    bindSingleton {
        ComponentDesignTokens(instance(), instance(), instance(), instance(), instance(), instance(), instance())
    }

    // All together.
    bindSingleton { MaterialDesignTokens(instance(), instance(), instance()) }

}
