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

    // Setup action.

    @Throws(Exception::class)
    override fun apply(target: Project) = target.run {
        val extension                = createPluginExtension()
        extension.codeGenerationTask = registerCatalogsGenerationTask(
                                           extension.packageName, extension.visibilityModifier, extension.catalogs
                                       )
    }



    // Private.

    @Throws(IllegalArgumentException::class)
    private fun Project.createPluginExtension(): PluginExtension =
        extensions.create("versionCatalogsGenerator", PluginExtension::class.java)
                  .apply {
                      visibilityModifier.convention(VisibilityModifier.PUBLIC)
                  }

    @Throws(Exception::class)
    private fun Project.registerCatalogsGenerationTask(
        packageName: Property<String>,
        visibilityModifier: Property<VisibilityModifier>,
        catalogs: NamedDomainObjectContainer<VersionCatalog>
    ): VersionCatalogsGenerationTask =
        tasks.register("generateVersionCatalogs", VersionCatalogsGenerationTask::class.java) {
            it.packageName.set(packageName)
            it.visibilityModifier.set(visibilityModifier)
            it.catalogs.set(catalogs)
            it.sourcesOutDirectory.set(sourcesOutDirectory)
        }.get()



    // Paths configuration.

    private inline val Project.sourcesOutDirectory get() = File(buildDir, "generated/versions")

}
