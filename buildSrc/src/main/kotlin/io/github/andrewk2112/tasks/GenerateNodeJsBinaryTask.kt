package io.github.andrewk2112.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import java.io.File

/**
 * Builds typical Node.js binaries.
 * */
abstract class GenerateNodeJsBinaryTask : DefaultTask() {

    // Public prerequisites.

    // Make sure Node.js modules are fetched.
    init {
        @Suppress("LeakingThis")
        dependsOn("kotlinNpmInstall")
    }

    /** The name of a library to generate the binary executable for. */
    @get:Input
    var libName: String? = null
        set(value) {
            if (field == value) return
            field = value
            libBaseDir = File(project.buildDir, "js/node_modules/$value-bin")
            onlyIf { !File(libBaseDir, "vendor/$value").isFile } // no need to invoke the compilation
                                                                 // if the binary exists
        }

    /** The location of a Node.js executable to be used. */
    @get:InputFile
    abstract val nodeJsBinary: RegularFileProperty



    // Private.

    @TaskAction
    private operator fun invoke() {
        project.exec {
            commandLine(nodeJsBinary.get(), File(libBaseDir, "lib/install.js"))
        }
    }

    /** Describes the location of a library to be compiled. */
    private lateinit var libBaseDir: File

}
