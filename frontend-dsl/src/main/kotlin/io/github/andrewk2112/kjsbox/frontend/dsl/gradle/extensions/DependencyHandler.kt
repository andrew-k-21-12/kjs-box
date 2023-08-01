package io.github.andrewk2112.kjsbox.frontend.dsl.gradle.extensions

import org.gradle.api.UnknownDomainObjectException
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.provider.Provider
import org.jetbrains.kotlin.gradle.targets.js.npm.BaseNpmDependencyExtension
import org.jetbrains.kotlin.gradle.targets.js.npm.DevNpmDependencyExtension
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmDependency
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmDependencyExtension

// Public.

/**
 * Retrieves a development-only [NpmDependency] from a [dependencyProvider].
 */
@Throws(
    ClassCastException::class,
    IllegalStateException::class,
    NullPointerException::class,
    UnknownDomainObjectException::class,
)
fun DependencyHandler.devNpm(dependencyProvider: Provider<MinimalExternalModuleDependency>): NpmDependency =
    getNpmDependency<DevNpmDependencyExtension>("devNpm", dependencyProvider)

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
    getNpmDependency<NpmDependencyExtension>("npm", dependencyProvider)



// Private.

/**
 * Retrieves an [NpmDependency] from a [dependencyProvider]
 * configured by the extension of type [T] looked up by the [extensionName].
 */
@Throws(
    ClassCastException::class,
    IllegalStateException::class,
    NullPointerException::class,
    UnknownDomainObjectException::class,
)
private inline fun <reified T : BaseNpmDependencyExtension> DependencyHandler.getNpmDependency(
    extensionName: String,
    dependencyProvider: Provider<MinimalExternalModuleDependency>
): NpmDependency {
    val dependency = dependencyProvider.get()
    return ((this as ExtensionAware).extensions.getByName(extensionName) as T)
        .invoke(dependency.name, dependency.version!!)
}
