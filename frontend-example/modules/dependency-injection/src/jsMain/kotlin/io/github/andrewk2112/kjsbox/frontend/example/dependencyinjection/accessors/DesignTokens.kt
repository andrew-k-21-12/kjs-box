package io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.accessors

import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.rootComponent
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.ReferenceDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.SystemDesignTokens
import org.kodein.di.instance

/**
 * Provides access to all types of design tokens without pulling any additional dependencies.
 */
object DesignTokens {
    val reference: ReferenceDesignTokens by rootComponent.instance()
    val system:    SystemDesignTokens    by rootComponent.instance()
}
