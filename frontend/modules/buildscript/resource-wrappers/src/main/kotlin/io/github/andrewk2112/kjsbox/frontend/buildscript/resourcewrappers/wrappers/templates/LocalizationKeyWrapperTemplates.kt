package io.github.andrewk2112.kjsbox.frontend.buildscript.resourcewrappers.wrappers.templates

import org.intellij.lang.annotations.Language

/**
 * Basic templates to generate the code for localization keys' wrappers.
 */
internal class LocalizationKeyWrapperTemplates {

    // Utility.

    /**
     * Holds only those properties required to generate the code for a single localization key wrapper.
     */
    internal class LocalizationKey(internal val propertyName: String, internal val localizationKey: String)



    // API.

    /**
     * Generates the code of a file
     * containing a group of related (according to the [keysNamespace]) localization [keys].
     */
    @Language("kotlin")
    internal fun inflateLocalizationKeys(
        packageName: String,
        keysNamespace: String,
        keys: List<LocalizationKey>,
    ): String = """
package $packageName

const val namespace = "$keysNamespace"

${keys.joinToString("\n") { it.inflate() }}

    """.trimIndent()



    // Private.

    @Language("kotlin")
    private fun LocalizationKey.inflate(): String = """
const val $propertyName = "$localizationKey"
    """.trimIndent()

}
