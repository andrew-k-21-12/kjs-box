package io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer

import io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer.action.PatchingDeployAction
import okio.FileSystem
import okio.SYSTEM

fun main(args: Array<String>) {
    val fileSystem        = FileSystem.SYSTEM
    val commandLineInputs = CommandLineInterface(args, fileSystem)
    val printer           = ProgressAndResultPrinter()
    PatchingDeployAction(
        fileSystem,
        FileMetadataWriter(),
        commandLineInputs.sourceBundle,
        commandLineInputs.deploymentDestination,
        printer::printProgress
    )
        .execute()
        .apply(printer::printResult)
}
