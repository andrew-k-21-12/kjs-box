package io.github.andrewk2112.kjsbox.frontend.buildscript.shared.gradle

import org.gradle.api.Project

/**
 * Notifies about a registration of an entry point module.
 */
interface EntryPointModuleCallback {
    fun onEntryPointModuleRegistered(entryPointModule: Project)
}
