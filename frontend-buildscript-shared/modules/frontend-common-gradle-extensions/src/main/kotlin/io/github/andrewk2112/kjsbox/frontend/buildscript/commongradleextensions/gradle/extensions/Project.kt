package io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.gradle.extensions

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.UnknownDomainObjectException
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension

/**
 * Configures [KotlinJsProjectExtension] for Kotlin/JS [Project]s.
 */
@Throws(UnknownDomainObjectException::class)
fun Project.kotlinJs(action: KotlinJsProjectExtension.() -> Unit) =
    extensions.configure("kotlin", Action(action))
