package io.github.andrewk2112.kjsbox.frontend.gradle.tasks.actions

import io.github.andrewk2112.kjsbox.frontend.extensions.createSymbolicLinkTo
import io.github.andrewk2112.kjsbox.frontend.extensions.joinWithPath
import io.github.andrewk2112.kjsbox.frontend.gradle.tasks.WrappersGenerationTask
import java.io.IOException
import java.nio.file.InvalidPathException
import kotlin.jvm.Throws

/**
 * Creates symbolic links pointing to the [WrappersGenerationTask.targetResourcesDirectory].
 */
internal class CreateSymLinkToResourcesAction(private val task: WrappersGenerationTask) {

    /**
     * Creates a symbolic link from the [WrappersGenerationTask.resourcesOutDirectory]
     * joined with the [WrappersGenerationTask.resourcesTypeName] and [WrappersGenerationTask.moduleName].
     *
     * @return A unique subpath identifying the exact group of the target resources.
     */
    @Throws(
        FileAlreadyExistsException::class,
        IllegalStateException::class,
        InvalidPathException::class,
        IOException::class,
        SecurityException::class,
        UnsupportedOperationException::class
    )
    internal fun createFromResourcesTypeAndModuleName(): String = "${task.resourcesTypeName}/${task.moduleName}".also {
        task.resourcesOutDirectory.asFile.get()
            .joinWithPath(it)
            .createSymbolicLinkTo(task.targetResourcesDirectory)
    }

    /**
     * Creates a structure of folders and symbolic links to organize all locales for their bundled state.
     */
    @Throws(
        FileAlreadyExistsException::class,
        IllegalStateException::class,
        InvalidPathException::class,
        IOException::class,
        NullPointerException::class,
        SecurityException::class,
        UnsupportedOperationException::class
    )
    internal fun createFromLocaleDirectories() {
        val localesOutDirectory = task.resourcesOutDirectory.asFile.get()
                                      .joinWithPath(task.resourcesTypeName!!) // safe as should be validated already
        for (localeDirectory in task.targetResourcesDirectory.listFiles()!!) {
            localesOutDirectory.joinWithPath(localeDirectory.name)    // <out>/<locales>/<language>
                               .joinWithPath(task.moduleName!!)       // <out>/<locales>/<language>/<module>
                               .createSymbolicLinkTo(localeDirectory) // a source dir with all translations for a locale
        }
    }

}
