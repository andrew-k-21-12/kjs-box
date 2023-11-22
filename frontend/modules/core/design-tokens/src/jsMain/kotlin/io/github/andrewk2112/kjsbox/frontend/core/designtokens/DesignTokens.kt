package io.github.andrewk2112.kjsbox.frontend.core.designtokens

/**
 * All groups of design tokens.
 */
interface DesignTokens {
    val reference: ReferenceDesignTokens
    val system:    SystemDesignTokens<*>
    val component: Any
}
