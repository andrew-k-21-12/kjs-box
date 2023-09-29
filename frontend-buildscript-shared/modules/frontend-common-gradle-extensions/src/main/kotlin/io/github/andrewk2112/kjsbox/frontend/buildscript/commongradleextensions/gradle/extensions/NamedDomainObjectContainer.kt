package io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.gradle.extensions

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.UnknownDomainObjectException
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

/**
 * Syntax sugar to find and get `jsMain` [KotlinSourceSet].
 */
@Throws(IllegalStateException::class, UnknownDomainObjectException::class)
fun NamedDomainObjectContainer<KotlinSourceSet>.jsMain(): KotlinSourceSet = named("jsMain").get()

/**
 * Syntax sugar to find and configure `jsMain` [KotlinSourceSet].
 */
@Throws(UnknownDomainObjectException::class)
fun NamedDomainObjectContainer<KotlinSourceSet>.jsMain(configuration: KotlinSourceSet.() -> Unit) {
    named("jsMain", configuration)
}
