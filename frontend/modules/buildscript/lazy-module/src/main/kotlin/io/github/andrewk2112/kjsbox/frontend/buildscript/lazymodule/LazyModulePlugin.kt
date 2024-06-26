package io.github.andrewk2112.kjsbox.frontend.buildscript.lazymodule

import io.github.andrewk2112.kjsbox.frontend.buildscript.versioncatalogs.KotlinVersionCatalog
import io.github.andrewk2112.utility.common.extensions.joinWithPath
import io.github.andrewk2112.utility.gradle.extensions.applyMultiplatform
import io.github.andrewk2112.utility.gradle.extensions.createExtension
import io.github.andrewk2112.utility.gradle.extensions.registerTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

/**
 * Describes any Gradle module which is going to be lazy (loaded on demand).
 */
internal class LazyModulePlugin : Plugin<Project> {

    // Action.

    @Throws(Exception::class)
    override fun apply(target: Project): Unit = target.run {

        val lazyModule               by createExtension<LazyModulePluginExtension>()
        val generateLazyExportConfig by registerTask<LazyExportConfigGenerationTask> {
            moduleName.set(this@run.name)
            componentToExport.set(lazyModule.exportedComponent)
            sourcesOutDirectory.set(generatedLazyExportConfigDirectory)
        }

        applyMultiplatform {
            js().browser()
            sourceSets.jsMain {
                kotlin.srcDir(generateLazyExportConfig)
                dependencies {
                    val kotlinVersionCatalog = KotlinVersionCatalog()
                    implementation(dependencies.platform(kotlinVersionCatalog.libraries.kotlinWrappersBom.fullNotation))
                    implementation(kotlinVersionCatalog.libraries.kotlinWrappersReact.fullNotation)
                }
            }
        }

        // Enabling the compilation of this on-demand module.
        rootProject.dependencies.add("jsMainImplementation", project)

    }



    // Config.

    /** Where to save export-configuring Kotlin sources. */
    private inline val Project.generatedLazyExportConfigDirectory: File
        get() = layout.buildDirectory.asFile.get().joinWithPath("generated/export")

}
