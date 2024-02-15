package io.github.andrewk2112.kjsbox.frontend.designtokens

/**
 * Context-dependent style values divided into groups.
 */
interface SystemDesignTokens<C> {
    val fontSizes:  ContextedFontSizes<C>
    val fontStyles: ContextedFontStyles<C>
    val opacities:  ContextedOpacities<C>
    val palette:    ContextedPalette<C>
    val radii:      ContextedRadii<C>
    val sizes:      ContextedSizes<C>
    val spacing:    ContextedSpacing<C>
    val time:       ContextedTime<C>
    val timing:     ContextedTiming<C>
}
