package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.wrappers.writers.independent

import io.github.andrewk2112.utility.common.extensions.ensureDirectoryExistsOrThrow
import io.github.andrewk2112.utility.common.extensions.joinWithPath
import io.github.andrewk2112.kjsbox.frontend.buildscript.commongradleextensions.extensions.*
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.models.HavingRelativePath
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.wrappers.writers.WrapperWritingException
import io.github.andrewk2112.utility.string.formats.changeFormat
import io.github.andrewk2112.utility.string.formats.other.PackageName
import io.github.andrewk2112.utility.string.formats.other.UniversalPath
import io.github.andrewk2112.utility.string.extensions.modifyIfNotEmpty
import java.io.File

/**
 * Groups the family of wrappers writers for resources which do not depend on any other classes.
 */
internal abstract class IndependentWrappersWriter<T : HavingRelativePath> {

    // API.

    /**
     * Does all preliminary preparations and checks,
     * writes a [resource] wrapper into [allWrappersOutDirectory] with [basePackageName].
     */
    @Throws(WrapperWritingException::class)
    internal fun writeWrapper(allWrappersOutDirectory: File, basePackageName: String, resource: T) {
        try {

            // Where to write a particular resource.
            val wrapperOutDirectory = allWrappersOutDirectory
                .joinWithPath(
                    basePackageName.changeFormat(PackageName, UniversalPath)
                )
                .joinWithPath(resource.relativePath)

            // Making sure the destination directory exists.
            wrapperOutDirectory.ensureDirectoryExistsOrThrow(
                "Can not create the output directory for a wrapper: ${wrapperOutDirectory.absolutePath}"
            )

            // Preparing the target package name for the wrapper.
            val wrapperPackageName = basePackageName +
                                     resource.relativePath.modifyIfNotEmpty {
                                         ".${it.changeFormat(UniversalPath, PackageName)}"
                                     }

            performWrapperWriting(wrapperOutDirectory, wrapperPackageName, resource)

        } catch (exception: Exception) {
            throw WrapperWritingException(exception)
        }
    }



    // Protected - to be inherited.

    /**
     * Performs the actual wrapper writing after all preliminary preparations and checks.
     */
    @Throws(Exception::class)
    protected abstract fun performWrapperWriting(wrapperOutDirectory: File, wrapperPackageName: String, resource: T)

}
