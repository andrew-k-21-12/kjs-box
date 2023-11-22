package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.accessors

import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection.materialDesignComponent
import io.github.andrewk2112.kjsbox.frontend.example.materialdesign.designtokens.MaterialDesignTokens
import org.kodein.di.instance

/**
 * Provides access to material design tokens without pulling any additional dependencies.
 */
val materialDesignTokens: MaterialDesignTokens by materialDesignComponent.instance()
