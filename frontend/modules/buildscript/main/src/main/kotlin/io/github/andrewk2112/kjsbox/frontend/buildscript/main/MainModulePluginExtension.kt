package io.github.andrewk2112.kjsbox.frontend.buildscript.main

import org.gradle.api.provider.Property

interface MainModulePluginExtension {

    /**
     * Sets a custom directory name to place all static sources and resources of the generated bundle into.
     * By default, if no value is provided, the version name is used.
     */
    val customBundleStaticsDirectory: Property<String>

}
