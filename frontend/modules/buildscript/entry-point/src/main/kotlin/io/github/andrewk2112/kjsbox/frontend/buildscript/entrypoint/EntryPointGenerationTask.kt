package io.github.andrewk2112.kjsbox.frontend.buildscript.entrypoint

import io.github.andrewk2112.utility.common.extensions.joinWithPath
import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

/**
 * Basic class to provide various ways of the app's entry point generation.
 */
internal abstract class EntryPointGenerationTask : DefaultTask() {

    // Extendable configuration.

    /**
     * Should return Kotlin code to be used as the entry point.
     */
    @Throws(Exception::class)
    internal abstract fun generateEntryPointCode(): String



    // Basic configuration.

    /** Where to write generated sources of the entry point. */
    @get:OutputDirectory
    internal abstract val sourcesOutDirectory: RegularFileProperty



    // Action.

    @TaskAction
    @Throws(Exception::class)
    private fun generateEntryPointAndWriteToFile() = sourcesOutDirectory.get().asFile
                                                                        .joinWithPath(entryPointFileName)
                                                                        .writeText(generateEntryPointCode())



    // Static configuration.

    /** This filename will be used as a destination to write entry point sources into. */
    private inline val entryPointFileName get() = "main.kt"

}
