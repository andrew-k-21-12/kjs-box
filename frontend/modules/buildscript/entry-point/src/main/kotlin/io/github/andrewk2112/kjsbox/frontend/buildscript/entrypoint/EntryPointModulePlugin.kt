package io.github.andrewk2112.kjsbox.frontend.buildscript.entrypoint

import io.github.andrewk2112.kjsbox.frontend.buildscript.shared.gradle.EntryPointModuleCallback
import io.github.andrewk2112.utility.common.extensions.joinWithPath
import io.github.andrewk2112.kjsbox.frontend.buildscript.versioncatalogs.KotlinVersionCatalog
import io.github.andrewk2112.utility.gradle.extensions.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

/**
 * Must be applied to a Gradle module which is going to be an app's entry point:
 * make sure there is the only Gradle module applying it.
 */
internal class EntryPointModulePlugin : Plugin<Project> {

    // Action.

    @Throws(Exception::class)
    override fun apply(target: Project): Unit = target.run {

        val entryPoint         by createExtension<EntryPointModulePluginExtension>()
        val generateEntryPoint by registerTask<EntryPointGenerationTask> {
            rootComponentName.set(entryPoint.rootComponent)
            sourcesOutDirectory.set(generatedEntryPointDirectory)
        }

        applyMultiplatform {
            js().browser()
            sourceSets.jsMain {
                kotlin.srcDir(generateEntryPoint)
                dependencies {
                    val kotlinVersionCatalog = KotlinVersionCatalog()
                    implementation(dependencies.platform(kotlinVersionCatalog.libraries.kotlinWrappersBom.fullNotation))
                    implementation(kotlinVersionCatalog.libraries.kotlinWrappersStyledNext.fullNotation)
                }
            }
        }

        notifyRootProjectAboutRegistration()

    }



    // Private.

    @Throws(IllegalStateException::class)
    private fun Project.notifyRootProjectAboutRegistration() {
        rootProject.plugins
            .filterIsInstance(EntryPointModuleCallback::class.java)
            .takeIf { it.isNotEmpty() }
            ?.forEach {
                it.onEntryPointModuleRegistered(this)
            }
            ?: throw IllegalStateException(
                "No plugins implementing ${EntryPointModuleCallback::class.java} are applied to the root project"
            )
    }



    // Configs.

    /** Where to save entry point Kotlin sources. */
    private inline val Project.generatedEntryPointDirectory: File
        get() = layout.buildDirectory.asFile.get().joinWithPath("generated/entry")

}
