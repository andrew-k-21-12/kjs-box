package io.github.andrewk2112.kjsbox.frontend.buildscript.lazymoduleaccessors

import io.github.andrewk2112.utility.common.extensions.joinWithPath
import io.github.andrewk2112.utility.gradle.extensions.createExtension
import io.github.andrewk2112.utility.gradle.extensions.registerTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

/**
 * Generates entry point component sources to navigate to lazy modules.
 *
 * The sources are generated inside lazy modules' directories to be reusable.
 */
internal class LazyModuleAccessorsPlugin : Plugin<Project> {

    // Action.

    @Throws(Exception::class)
    override fun apply(target: Project): Unit = target.run {
        val lazyModuleAccessors by createExtension<LazyModuleAccessorsPluginExtension>()
        lazyModuleAccessors.accessorsProvider = { targetProjects ->
            targetProjects.map { targetProject ->
                targetProject.tasks.withType(LazyEntryComponentGenerationTask::class.java)
                                   .firstOrNull()
                                   ?.run {
                                       return@map sourcesOutDirectory
                                   }
                val generateLazyEntryComponent by targetProject.registerTask<LazyEntryComponentGenerationTask> {
                    rootProjectName.set(targetProject.rootProject.name)
                    moduleName.set(targetProject.name)
                    sourcesOutDirectory.set(targetProject.lazyEntryComponentDirectory)
                }
                generateLazyEntryComponent.sourcesOutDirectory
            }
        }
    }



    // Paths.

    /** Where to write output entry component sources. */
    private inline val Project.lazyEntryComponentDirectory: File
        get() = layout.buildDirectory.asFile.get().joinWithPath("generated/entry")

}
