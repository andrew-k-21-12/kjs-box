package io.github.andrewk2112.kjsbox.frontend.buildscript.lazymodule

import org.gradle.api.provider.Property

interface LazyModulePluginExtension {

    /** The full name of component to be exported as an entry point. */
    val exportedComponent: Property<String>

}
