package io.github.andrewk2112.kjsbox.frontend.buildscript.gradle.extensions

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.UnknownDomainObjectException
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension

/**
 * Configures [KotlinJsProjectExtension] for Kotlin/JS [Project]s.
 */
@Throws(UnknownDomainObjectException::class)
internal fun Project.kotlinJs(action: KotlinJsProjectExtension.() -> Unit) =
    extensions.configure("kotlin", Action(action))
