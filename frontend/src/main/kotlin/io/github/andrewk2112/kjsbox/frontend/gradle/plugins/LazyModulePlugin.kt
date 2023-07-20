package io.github.andrewk2112.kjsbox.frontend.gradle.plugins

import io.github.andrewk2112.kjsbox.frontend.gradle.extensions.kotlinJs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType
import org.jetbrains.kotlin.gradle.plugin.KotlinJsPluginWrapper

/**
 * Describes any Gradle module which is going to be lazy (loaded on demand).
 */
internal class LazyModulePlugin : Plugin<Project> {

    @Throws(Exception::class)
    override fun apply(target: Project): Unit = target.run {

        plugins.apply(KotlinJsPluginWrapper::class.java)

        kotlinJs {
            js(KotlinJsCompilerType.IR).browser()
        }

        // Enabling the compilation of this on-demand module.
        project.rootProject.dependencies.add("implementation", project)

    }

}
