package io.github.andrewk2112.kjsbox.frontend.buildscript.entrypoint

import org.gradle.api.provider.Property

interface EntryPointModulePluginExtension {

    /** The full name of component to be an entry point. */
    val rootComponent: Property<String>

}
