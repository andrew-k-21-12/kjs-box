package io.github.andrewk2112.kjsbox.frontend.buildscript.lazymoduleaccessors

import org.gradle.api.Project
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.internal.catalog.DelegatingProjectDependency

/**
 * An interface to configure [LazyModuleAccessorsPlugin].
 */
abstract class LazyModuleAccessorsPluginExtension {

    /**
     * Registers accessor-generating tasks for each of provided [targetProjects]
     * and returns directories where the generated sources are going to be located.
     */
    @Throws(Exception::class)
    fun generateOrGetFor(vararg targetProjects: DelegatingProjectDependency): List<RegularFileProperty> =
        accessorsProvider.invoke(
            targetProjects.map { it.dependencyProject }
        )

    /** Contains the actual logic for [generateOrGetFor], must be set by the plugin. */
    internal lateinit var accessorsProvider: (List<Project>) -> List<RegularFileProperty>

}
