package io.github.andrewk2112.kjsbox.frontend.buildscript.entrypoint

import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.intellij.lang.annotations.Language

/**
 * Generates and writes an entry point source file
 * which uses a fully customizable bootstrap function to set up everything.
 */
internal abstract class SelfWrittenEntryPointGenerationTask : EntryPointGenerationTask() {

    @Language("kotlin")
    @Throws(Exception::class)
    override fun generateEntryPointCode(): String = """
/** The entry point - runs a fully customizable bootstrap function. */
@EagerInitialization
@OptIn(ExperimentalStdlibApi::class)
@Suppress("unused", "DEPRECATION")
private val main = ${customInitializationFunctionName.get()}()

    """.trimIndent()

    /** Full name (including package) of a function to be bootstrapped as an entry point. */
    @get:Input
    internal abstract val customInitializationFunctionName: Property<String>

}
