package io.github.andrewk2112.versioncatalogsgenerator.gradle

import io.github.andrewk2112.commonutility.extensions.joinWithPath
import io.github.andrewk2112.gradleutility.extensions.createExtension
import io.github.andrewk2112.gradleutility.extensions.registerTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

/**
 * Setups all environment to generate version catalog wrappers.
 */
internal class Plugin : Plugin<Project> {

    // Setup action.

    @Throws(Exception::class)
    override fun apply(target: Project): Unit = target.run {
        val versionCatalogsGenerator by createExtension<PluginExtension>()
        val generateVersionCatalogs  by registerTask<VersionCatalogsGenerationTask> {
            packageName.set(versionCatalogsGenerator.packageName)
            visibilityModifier.set(versionCatalogsGenerator.visibilityModifier)
            catalogs.set(versionCatalogsGenerator.catalogs)
            sourcesOutDirectory.set(target.sourcesOutDirectory)
        }
        versionCatalogsGenerator.apply {
            visibilityModifier.convention(VisibilityModifier.PUBLIC)
            codeGenerationTask = generateVersionCatalogs
        }
    }



    // Paths.

    @get:Throws(IllegalStateException::class)
    private inline val Project.sourcesOutDirectory: File
        get() = layout.buildDirectory.asFile.get().joinWithPath("generated/versions")

}
