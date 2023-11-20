package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.accessors

import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.materialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.ReferenceDesignTokens
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.SystemDesignTokens
import org.kodein.di.instance

/**
 * Provides access to material design tokens without pulling any additional dependencies.
 */
object MaterialDesignTokens {
    val reference: ReferenceDesignTokens by materialDesignComponent.instance()
    val system:    SystemDesignTokens    by materialDesignComponent.instance()
}
