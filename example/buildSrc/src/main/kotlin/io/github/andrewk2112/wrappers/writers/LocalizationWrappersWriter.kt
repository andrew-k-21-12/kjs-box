package io.github.andrewk2112.wrappers.writers

import io.github.andrewk2112.extensions.*
import io.github.andrewk2112.extensions.dotsToSlashes
import io.github.andrewk2112.extensions.ensureDirectoryExistsOrThrow
import io.github.andrewk2112.extensions.modifyIfNotEmpty
import io.github.andrewk2112.wrappers.templates.LocalizationKeyWrapperTemplates
import io.github.andrewk2112.wrappers.templates.LocalizationKeyWrapperTemplates.LocalizationKey
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
                .joinWithPath(basePackageName.dotsToSlashes())
                .joinWithPath(relativePath)
                .also {
                    it.ensureDirectoryExistsOrThrow(
                        "Can not create the output directory for a keys holder: ${it.absolutePath}"
                    )
                }

            // Preparing a target package name for the keys' holder.
            val keysHolderPackageName = basePackageName +
                                        relativePath.modifyIfNotEmpty { ".${it.toValidPackage()}" }

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
    private fun String.generateLocalizationKeysFilename(): String = split("-").joinCapitalized() + "LocalizationKeys"

}
