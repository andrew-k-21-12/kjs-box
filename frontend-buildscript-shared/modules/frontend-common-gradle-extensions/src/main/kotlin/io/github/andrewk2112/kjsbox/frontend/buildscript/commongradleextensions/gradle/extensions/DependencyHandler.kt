package io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.gradle.extensions

import org.gradle.api.UnknownDomainObjectException
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.targets.js.npm.DevNpmDependencyExtension
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmDependency
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmDependencyExtension



// Public.

/**
 * Retrieves an [NpmDependency] from a [dependencyProvider].
 */
@Throws(
    ClassCastException::class,
    IllegalStateException::class,
    NullPointerException::class,
    UnknownDomainObjectException::class,
)
fun DependencyHandler.npm(dependencyProvider: Provider<MinimalExternalModuleDependency>): NpmDependency =
    with(dependencyProvider.get()) { npmDependencyExtension(name, version!!) }

/**
 * Retrieves an [NpmDependency] from its [name] and [version].
 */
@Throws(ClassCastException::class, UnknownDomainObjectException::class)
fun DependencyHandler.npm(name: String, version: String): NpmDependency = npmDependencyExtension(name, version)

/**
 * Retrieves a development-only [NpmDependency] from its [name] and [version].
 */
@Throws(ClassCastException::class, UnknownDomainObjectException::class)
fun DependencyHandler.devNpm(name: String, version: String): NpmDependency = devNpmDependencyExtension(name, version)



// Private.

/** Retrieves an [NpmDependencyExtension] to declare corresponding dependencies. */
@get:Throws(ClassCastException::class, UnknownDomainObjectException::class)
private val DependencyHandler.npmDependencyExtension: NpmDependencyExtension
    get() = extensions.getByName("npm") as NpmDependencyExtension

/** Retrieves a [DevNpmDependencyExtension] to declare corresponding dependencies. */
@get:Throws(ClassCastException::class, UnknownDomainObjectException::class)
private val DependencyHandler.devNpmDependencyExtension: DevNpmDependencyExtension
    get() = extensions.getByName("devNpm") as DevNpmDependencyExtension
