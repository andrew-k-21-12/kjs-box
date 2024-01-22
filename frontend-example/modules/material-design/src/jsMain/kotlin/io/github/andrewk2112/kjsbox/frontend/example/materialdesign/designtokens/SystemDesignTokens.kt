package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.*
import io.github.andrewk2112.kjsbox.frontend.core.designtokens.SystemDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.Context
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system.SystemFontSizes
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system.SystemFontStyles
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system.SystemPalette
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.system.SystemSizes

class SystemDesignTokens(
    override val fontSizes:  SystemFontSizes,
    override val fontStyles: SystemFontStyles,
    override val palette:    SystemPalette,
    override val sizes:      SystemSizes,
) : SystemDesignTokens<Context> {
    override val opacities = object : ContextedOpacities<Context>() {}
    override val radii     = object : ContextedRadii<Context>() {}
    override val spacing   = object : ContextedSpacing<Context>() {}
    override val time      = object : ContextedTime<Context>() {}
    override val timing    = object : ContextedTiming<Context>() {}
}
