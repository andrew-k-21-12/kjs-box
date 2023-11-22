package io.github.andrewk2112.kjsbox.frontend.example.designtokens

import io.github.andrewk2112.kjsbox.frontend.core.designtokens.DesignTokens

class DesignTokens(
    override val reference: ReferenceDesignTokens,
    override val system:    SystemDesignTokens
) : DesignTokens {
    override val component: Any = Unit
}
