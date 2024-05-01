package io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer

import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.ParameterHolder
import com.github.ajalt.clikt.parameters.options.*
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath

/**
 * Setups all arguments to be received from the command line with their descriptions and basic validation.
 */
internal class CommandLineInterface(
    mainArguments: Array<String>,
    private val fileSystem: FileSystem,
) : NoOpCliktCommand() {

    val sourceBundle:          Path by bundleDirectory("The \"$BUNDLE_DIRECTORY\" folder of a bundle to be deployed")
    val deploymentDestination: Path by bundleDirectory("A \"$BUNDLE_DIRECTORY\" destination folder to deploy to")

    // This initialization call must be invoked after the declaration of all arguments above.
    init {
        main(mainArguments)
    }

    /**
     * Syntax sugar to declare required directory options
     * having names equal to [BUNDLE_DIRECTORY] and [help] descriptions.
     */
    private fun ParameterHolder.bundleDirectory(help: String): OptionDelegate<Path> =
        option()
            .help(help)
            .convert("directory") {
                val path = it.toPath()
                when {
                    path.name != BUNDLE_DIRECTORY -> fail("The directory name is not equal to \"$BUNDLE_DIRECTORY\"")
                    this@CommandLineInterface.fileSystem.metadataOrNullWithoutThrows(path)?.isDirectory != true ->
                        fail("\"$path\" is not a directory")
                    else -> path
                }
            }.required()

    private companion object {

        /** Expected target directory names config. */
        private const val BUNDLE_DIRECTORY = "static"

    }

}
