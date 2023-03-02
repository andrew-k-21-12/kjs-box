package io.github.andrewk2112.gradle

/**
 * Provides a Kotlin wrapper dependency name for the required [targetLibrary].
 */
fun kotlinWrapper(targetLibrary: String): String = "org.jetbrains.kotlin-wrappers:kotlin-$targetLibrary"