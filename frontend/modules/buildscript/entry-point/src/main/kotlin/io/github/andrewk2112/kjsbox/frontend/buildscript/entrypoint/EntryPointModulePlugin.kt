package io.github.andrewk2112.kjsbox.frontend.buildscript.entrypoint

import io.github.andrewk2112.utility.common.extensions.joinWithPath
import io.github.andrewk2112.kjsbox.frontend.buildscript.shared.gradle.tasks.DirectoryWritingTask
import io.github.andrewk2112.kjsbox.frontend.buildscript.shared.gradle.tasks.actions.writeintodirectory.TextWriteIntoDirectoryAction
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
                    implementation(platform(kotlinVersionCatalog.libraries.kotlinWrappersBom.fullNotation))
                    implementation(kotlinVersionCatalog.libraries.kotlinWrappersStyledNext.fullNotation)
                }
            }
        }

        // On-demand modules require special processing to be compiled - this is the way to request the compilation.
        rootProject.dependencies.add("jsMainImplementation", project)

        // There are no any clean or obvious ways to use JS-only webpack configs or configure webpack only inside Gradle,
        // so using this dirty hack with appending a special webpack-configuring JS file to set up the app's entry point.
        rootProject.findTask<DirectoryWritingTask>("unpackWebpackConfigs")
                   .addAction(
                       TextWriteIntoDirectoryAction(entryPointJsCode, entryPointJsFileName)
                   )

    }



    // Configs.

    /** Where to save entry point Kotlin sources. */
    private inline val Project.generatedEntryPointDirectory: File
        get() = layout.buildDirectory.asFile.get().joinWithPath("generated/entry")

    /** JS code to set up the app's entry point for webpack. */
    private inline val Project.entryPointJsCode: String
        get() = "config.entry = require(\"path\").resolve(__dirname, `\${RAW_OUTPUT_DIR}/\${config.output.library}-$name.js`);\n"

    /** How to name the entry point-configuring JS file. */
    private inline val entryPointJsFileName get() = "3-entry.js"

}
