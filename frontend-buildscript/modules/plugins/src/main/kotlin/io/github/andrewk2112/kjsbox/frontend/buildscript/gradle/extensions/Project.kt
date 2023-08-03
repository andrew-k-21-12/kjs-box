package io.github.andrewk2112.kjsbox.frontend.buildscript.gradle.extensions

import org.gradle.api.Action
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Project
import org.gradle.api.UnknownDomainObjectException
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension
import org.jetbrains.kotlin.gradle.targets.js.npm.DevNpmDependencyExtension
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmDependencyExtension

/**
 * Adds an NPM dependency with a [libraryAlias] from a TOML catalog with a [tomlCatalogName]
 * to the corresponding [configuration] with [isDev] (development-only) mode.
 */
@Throws(
    ClassCastException::class,
    IllegalStateException::class,
    InvalidUserDataException::class,
    NoSuchElementException::class,
    NullPointerException::class,
    UnknownDomainObjectException::class,
)
internal fun Project.addNpmDependency(
    tomlCatalogName: String,
    libraryAlias: String,
    configuration: String,
    isDev: Boolean
) {
    val library = rootProject.extensions
                             .getByType(VersionCatalogsExtension::class.java)
                             .named(tomlCatalogName)
                             .findLibrary(libraryAlias)
                             .get()
                             .get()
    val dependency = (dependencies as ExtensionAware).extensions.run {
        if (isDev) {
            getByName("devNpm") as DevNpmDependencyExtension
        } else {
            getByName("npm") as NpmDependencyExtension
        }
    }.invoke(library.name, library.version!!)
    dependencies.add(configuration, dependency)
}

/**
 * Configures [KotlinJsProjectExtension] for Kotlin/JS [Project]s.
 */
@Throws(ClassCastException::class, UnknownDomainObjectException::class)
internal fun Project.kotlinJs(action: KotlinJsProjectExtension.() -> Unit) =
    (project as ExtensionAware).extensions.configure("kotlin", Action(action))
