package io.github.andrewk2112.versioncatalogsgenerator.gradle

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import java.io.File

/**
 * Setups all environment to generate version catalog wrappers.
 */
internal class Plugin : Plugin<Project> {

    @Throws(Exception::class)
    override fun apply(target: Project) = target.run {
        val extension                = createPluginExtension()
        extension.codeGenerationTask = registerCatalogsGenerationTask(extension.packageName, extension.catalogs)
    }

    @Throws(IllegalArgumentException::class)
    private fun Project.createPluginExtension(): PluginExtension =
        extensions.create("versionCatalogsGenerator", PluginExtension::class.java)

    @Throws(Exception::class)
    private fun Project.registerCatalogsGenerationTask(
        packageName: Property<String>,
        catalogs: NamedDomainObjectContainer<VersionCatalog>
    ): VersionCatalogsGenerationTask =
        tasks.register("generateVersionCatalogs", VersionCatalogsGenerationTask::class.java) {
            it.packageName.set(packageName)
            it.catalogs.set(catalogs)
            it.sourcesOutDirectory.set(sourcesOutDirectory)
        }.get()

    private inline val Project.sourcesOutDirectory get() = File(buildDir, "generated/versions")

}
