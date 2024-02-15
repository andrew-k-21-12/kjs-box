package io.github.andrewk2112.kjsbox.frontend.designtokens

/**
 * All groups of design tokens.
 */
interface DesignTokens {
    val reference: ReferenceDesignTokens
    val system:    SystemDesignTokens<*>
    val component: Any
}
