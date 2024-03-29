package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.wrappers.writers

import io.github.andrewk2112.utility.common.extensions.ensureDirectoryExistsOrThrow
import io.github.andrewk2112.utility.common.extensions.joinWithPath
import io.github.andrewk2112.utility.common.extensions.writeTo
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.wrappers.templates.LocalizationKeyWrapperTemplates
import io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.wrappers.templates.LocalizationKeyWrapperTemplates.LocalizationKey
import io.github.andrewk2112.utility.string.extensions.decapitalize
import io.github.andrewk2112.utility.string.extensions.joinCapitalized
import io.github.andrewk2112.utility.string.formats.cases.CamelCase
import io.github.andrewk2112.utility.string.formats.cases.KebabCase
import io.github.andrewk2112.utility.string.formats.changeFormat
import io.github.andrewk2112.utility.string.formats.other.PackageName
import io.github.andrewk2112.utility.string.formats.other.UniversalPath
import io.github.andrewk2112.utility.string.extensions.modifyIfNotEmpty
import java.io.File
import kotlin.jvm.Throws

/**
 * Inflates and writes localization keys to files.
 */
internal class LocalizationWrappersWriter(
    private val localizationKeyWrapperTemplates: LocalizationKeyWrapperTemplates = LocalizationKeyWrapperTemplates()
) {

    // API.

    /**
     * Does all preliminary preparations and checks and writes localization keys into the [allKeysOutDirectory].
     */
    @Throws(WrapperWritingException::class)
    internal fun writeLocalizationKeys(
        allKeysOutDirectory: File,
        moduleName: String,
        basePackageName: String,
        relativePath: String,
        name: String,
        keys: Array<String>
    ) {
        try {

            // Preparing a destination directory to write a keys holder to, making sure it exists.
            val keysOutDirectory = allKeysOutDirectory
                .joinWithPath(basePackageName.changeFormat(PackageName, UniversalPath))
                .joinWithPath(relativePath)
                .also {
                    it.ensureDirectoryExistsOrThrow(
                        "Can not create the output directory for a keys holder: ${it.absolutePath}"
                    )
                }

            // Preparing a target package name for the keys' holder.
            val keysHolderPackageName = basePackageName +
                                        relativePath.modifyIfNotEmpty {
                                            ".${it.changeFormat(UniversalPath, PackageName)}"
                                        }

            // Namespace for all keys of this batch.
            val keysNamespace = moduleName + "/" +
                                relativePath.modifyIfNotEmpty { "$it/" } +
                                name

            // Composing everything together and writing to a file.
            localizationKeyWrapperTemplates
                .inflateLocalizationKeys(
                    keysHolderPackageName,
                    keysNamespace,
                    keys.map {
                        LocalizationKey(it.generateKeyPropertyName(), "$keysNamespace:$it")
                    }
                )
                .writeTo(
                    keysOutDirectory.joinWithPath("${name.generateLocalizationKeysFilename()}.kt")
                )

        } catch (exception: Exception) {
            throw WrapperWritingException(exception)
        }
    }



    // Private.

    /**
     * Converts a raw key name into its corresponding property name.
     */
    private fun String.generateKeyPropertyName(): String = split(".").joinCapitalized().decapitalize() + "Key"

    /**
     * Converts a raw name into the corresponding file name to keep localization keys.
     */
    private fun String.generateLocalizationKeysFilename(): String =
        changeFormat(KebabCase, CamelCase) + "LocalizationKeys"

}
