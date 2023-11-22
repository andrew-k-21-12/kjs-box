package io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility.accessors

import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.rootComponent
import io.github.andrewk2112.kjsbox.frontend.example.designtokens.DesignTokens
import org.kodein.di.instance

/**
 * Provides access to all types of design tokens without pulling any additional dependencies.
 */
val designTokens: DesignTokens by rootComponent.instance()
