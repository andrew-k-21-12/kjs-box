package io.github.andrewk2112.kjsbox.frontend.buildscript.main

import org.gradle.api.provider.Property

interface MainModulePluginExtension {

    /**
     * Sets a custom directory name to place all static sources and resources of the generated bundle into.
     *
     * By default, if no value is provided, the version name is used.
     */
    val customBundleStaticsDirectory: Property<String>

    /**
     * Path to a custom template index HTML file from the resources directory to be used instead of a default one.
     *
     * Do not set this property to use a default template.
     *
     * **Attention** - do not use `"index.html"` to make webpack caching work properly for this file.
     */
    val customIndexHtmlTemplateFile: Property<String>

}
