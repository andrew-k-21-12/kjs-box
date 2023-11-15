package io.github.andrewk2112.kjsbox.frontend.example.designtokens

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.ReferenceDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.reference.*

class ReferenceDesignTokens(
    override val fontSizes: ReferenceFontSizes,
    override val opacities: ReferenceOpacities,
    override val palette:   ReferencePalette,
    override val radii:     ReferenceRadii,
    override val sizes:     ReferenceSizes,
    override val spacing:   ReferenceSpacing,
    override val time:      ReferenceTime,
    override val timing:    ReferenceTiming,
) : ReferenceDesignTokens
