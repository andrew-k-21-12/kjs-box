package io.github.andrewk2112.kjsbox.frontend.example.designtokens

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.*
import io.github.andrewk2112.kjsbox.frontend.core.designtokens.SystemDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.system.SystemFontSizes
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.system.SystemPalette

class SystemDesignTokens(
    override val fontSizes: SystemFontSizes,
    override val palette:   SystemPalette,
) : SystemDesignTokens<Context> {
    override val opacities = object : ContextedOpacities<Context>() {}
    override val radii     = object : ContextedRadii<Context>() {}
    override val sizes     = object : ContextedSizes<Context>() {}
    override val spacing   = object : ContextedSpacing<Context>() {}
    override val time      = object : ContextedTime<Context>() {}
    override val timing    = object : ContextedTiming<Context>() {}
}
