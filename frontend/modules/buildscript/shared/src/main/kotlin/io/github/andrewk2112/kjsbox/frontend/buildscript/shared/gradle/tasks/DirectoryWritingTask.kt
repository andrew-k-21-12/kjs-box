package io.github.andrewk2112.kjsbox.frontend.buildscript.shared.gradle.tasks

import io.github.andrewk2112.kjsbox.frontend.buildscript.shared.gradle.tasks.actions.writeintodirectory.SimpleWriteIntoDirectoryAction
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

/**
 * Performs various types of [writeActions] into an [outDirectory].
 */
abstract class DirectoryWritingTask : DefaultTask() {

    // Configurations.

    /**
     * Appends an [action] to the list of [writeActions] to be executed.
     */
    fun addAction(action: SimpleWriteIntoDirectoryAction) {
        writeActions.apply {
            set(
                if (!isPresent) arrayOf(action) else arrayOf(*get(), action)
            )
        }
    }

    /** All actions to be executed in the scope of this task. */
    @get:Input
    abstract val writeActions: Property<Array<SimpleWriteIntoDirectoryAction>>

    /** A destination output directory to place all writing results into. */
    @get:OutputDirectory
    abstract val outDirectory: DirectoryProperty



    // Private.

    @TaskAction
    @Throws(Exception::class)
    private fun runAllWriteActions() = writeActions.get().forEach {
        it.writeIntoDirectory(outDirectoryFile).throwOnFailure()
    }

    /** Minor optimization to avoid preparing the same file multiple times. */
    @get:Throws(IllegalStateException::class)
    private val outDirectoryFile by lazy(LazyThreadSafetyMode.NONE) { outDirectory.get().asFile }

}
