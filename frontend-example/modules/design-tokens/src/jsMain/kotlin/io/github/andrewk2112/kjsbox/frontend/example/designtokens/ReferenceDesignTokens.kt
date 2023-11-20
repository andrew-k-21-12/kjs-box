package io.github.andrewk2112.kjsbox.frontend.example.designtokens

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.*
import io.github.andrewk2112.kjsbox.frontend.core.designtokens.ReferenceDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.reference.ReferenceFontSizes
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.reference.ReferencePalette
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.reference.ReferenceSpacing

class ReferenceDesignTokens(
    override val fontSizes: ReferenceFontSizes,
    override val palette:   ReferencePalette,
    override val spacing:   ReferenceSpacing,
) : ReferenceDesignTokens {
    override val opacities = object : Opacities {}
    override val radii     = object : Radii {}
    override val sizes     = object : Sizes {}
    override val time      = object : Time {}
    override val timing    = object : Timing {}
}
