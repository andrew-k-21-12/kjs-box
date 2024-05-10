package io.github.andrewk2112.kjsbox.frontend.buildscript.main

import io.github.andrewk2112.utility.common.extensions.joinWithPath
import io.github.andrewk2112.utility.common.utility.changeMonitor
import org.apache.commons.io.IOUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

/**
 * Unpacks all resources listed by their [resourceNames] into an [outDirectory],
 * no intermediate paths of resources are preserved.
 */
internal abstract class ResourcesUnpackingTask : DefaultTask() {

    // All configurations required to be set.

    /** Names of all resources to be unpacked. */
    @get:Input
    var resourceNames: Array<String>? by changeMonitor(::setupUnpackingSourcesAndDestinations)

    /** A directory to unpack required resource into. */
    @get:Internal
    var outDirectory: File? by changeMonitor(::setupUnpackingSourcesAndDestinations)



    // All derived configurations.

    /**
     * Gradle's metadata to keep track only of unpacked resources' files to decide whether the task is not up-to-date.
     * Make sure to set all configurations above to prevent this metadata from crashing the task.
     */
    @get:OutputFiles
    protected abstract var unpackingSourcesAndDestinations: Map<String, File>

    private fun setupUnpackingSourcesAndDestinations() {
        val resourceNames = resourceNames ?: return
        val outDirectory  = outDirectory  ?: return
        unpackingSourcesAndDestinations = buildMap {
            resourceNames.forEach {
                put(it, outDirectory.joinWithPath(File(it).name))
            }
        }
    }



    // Action.

    @TaskAction
    @Throws(Exception::class)
    private operator fun invoke() {
        for ((resourceName, unpackingDestinationFile) in unpackingSourcesAndDestinations) {
            val input = javaClass.classLoader.getResourceAsStream(resourceName)
                     ?: throw IllegalArgumentException("No resource found: $resourceName")
            var output: OutputStream? = null
            try {
                output = FileOutputStream(unpackingDestinationFile)
                IOUtils.copy(input, output)
                output.flush()
            } finally {
                IOUtils.closeQuietly(input, output)
            }
        }
    }

}
