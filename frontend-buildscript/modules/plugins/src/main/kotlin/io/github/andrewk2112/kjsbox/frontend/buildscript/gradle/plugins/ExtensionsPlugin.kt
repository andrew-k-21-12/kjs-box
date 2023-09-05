package io.github.andrewk2112.kjsbox.frontend.buildscript.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Doesn't apply anything, required to expose public extensions.
 */
internal class ExtensionsPlugin : Plugin<Project> {

    override fun apply(target: Project) = Unit

}
