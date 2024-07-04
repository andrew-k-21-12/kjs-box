package io.github.andrewk2112.kjsbox.frontend.buildscript.entrypoint

import io.github.andrewk2112.kjsbox.frontend.buildscript.shared.gradle.EntryPointModuleCallback
import io.github.andrewk2112.utility.common.extensions.joinWithPath
import io.github.andrewk2112.kjsbox.frontend.buildscript.versioncatalogs.KotlinVersionCatalog
import io.github.andrewk2112.utility.gradle.extensions.*
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

/**
 * Must be applied to a Gradle module which is going to be the app's entry point:
 * make sure there is only one Gradle module applying it.
 */
internal class EntryPointModulePlugin : Plugin<Project> {

    // Action.

    @Throws(Exception::class)
    override fun apply(target: Project): Unit = target.run {
        val entryPoint by createExtension<EntryPointModulePluginExtension>()
        applyMultiplatform {
            js().browser()
            sourceSets.jsMain {
                afterEvaluate { // waiting for the extension's arguments
                    val (task, shouldAddDependencies) = registerEntryPointGenerationTask(entryPoint)
                    kotlin.srcDir(task)
                    if (shouldAddDependencies) {
                        dependencies {
                            val kotlinLibraries = KotlinVersionCatalog().libraries
                            implementation(dependencies.platform(kotlinLibraries.kotlinWrappersBom.fullNotation))
                            implementation(kotlinLibraries.kotlinWrappersReact.fullNotation)
                            implementation(kotlinLibraries.kotlinWrappersStyledNext.fullNotation)
                        }
                    }
                }
            }
        }
        notifyRootProjectAboutRegistration()
    }



    // Private.

    /**
     * Registers a task to generate entry point sources according to the [configs].
     *
     * @return Generated task and a [Boolean] flag pointing whether it's needed to include additional dependencies.
     */
    @Throws(IllegalArgumentException::class, IllegalStateException::class, InvalidUserDataException::class)
    private fun Project.registerEntryPointGenerationTask(
        configs: EntryPointModulePluginExtension
    ): Pair<EntryPointGenerationTask, Boolean> {
        val initializationFunction = configs.customInitializationFunction.orNull
        val rootComponent          = configs.rootComponent.orNull
        val generateEntryPoint by when {
            initializationFunction != null -> registerTask<SelfWrittenEntryPointGenerationTask> {
                                                  customInitializationFunctionName.set(initializationFunction)
                                              }
            rootComponent != null          -> registerTask<ComponentBasedEntryPointGenerationTask> {
                                                  rootComponentName.set(rootComponent)
                                              }
            else                           -> throw IllegalArgumentException(
                                                  "An initialization function or a root component must be set " +
                                                          "for the entry point"
                                              )
        }
        generateEntryPoint.sourcesOutDirectory.set(generatedEntryPointDirectory)
        return generateEntryPoint to (generateEntryPoint is ComponentBasedEntryPointGenerationTask)
    }

    @Throws(IllegalStateException::class)
    private fun Project.notifyRootProjectAboutRegistration() {
        rootProject.plugins
            .filterIsInstance<EntryPointModuleCallback>()
            .takeIf { it.isNotEmpty() }
            ?.forEach {
                it.onEntryPointModuleRegistered(this)
            }
            ?: throw IllegalStateException(
                "No plugins implementing ${EntryPointModuleCallback::class.java} are applied to the root project"
            )
    }



    // Configs.

    /** Where to write entry point Kotlin sources. */
    private inline val Project.generatedEntryPointDirectory: File
        get() = layout.buildDirectory.asFile.get().joinWithPath("generated/entry")

}
