package io.github.andrewk2112.versioncatalogsgenerator.gradle

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Task
import org.gradle.api.provider.Property

/**
 * All configurations to generate version catalog wrappers.
 */
abstract class PluginExtension {

    /** A package name to be used in all generated sources. */
    abstract val packageName: Property<String>

    /** A [VisibilityModifier] to generate all sources with. */
    abstract val visibilityModifier: Property<VisibilityModifier>

    /** All versions catalogs to generate wrappers for. */
    abstract val catalogs: NamedDomainObjectContainer<VersionCatalog>

    /**
     * An exposed task to generate version catalog wrappers.
     * It can be added as a source set directory, for example, to include all generated sources.
     */
    lateinit var codeGenerationTask: Task
        internal set

}
