package io.github.andrewk2112.kjsbox.frontend.buildscript.entrypoint

import org.gradle.api.provider.Property

interface EntryPointModulePluginExtension {

    /**
     * Full name of a function to initialize the entry point,
     * **it must have no arguments** and be `public` or `internal`.
     *
     * Check possible implementation of such function
     * by reviewing the contents of [run] inside [ComponentBasedEntryPointGenerationTask.generateEntryPointCode].
     *
     * If this property is set, [rootComponent] configuration is ignored.
     */
    val customInitializationFunction: Property<String>

    /**
     * Full name of component to be set as an entry point.
     *
     * **Attention** - this configuration will be ignored if [customInitializationFunction] is set.
     */
    val rootComponent: Property<String>

}
