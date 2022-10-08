package io.github.andrewk2112.extensions

import org.gradle.api.Project
import org.gradle.api.Task

/**
 * Finds the first [Task] of provided type [T] or throws [NoSuchElementException] if nothing is found.
 * */
@Throws(NoSuchElementException::class)
inline fun <reified T : Task> Project.rootTaskOfType(): T = rootProject.tasks.withType(T::class.java).first()
