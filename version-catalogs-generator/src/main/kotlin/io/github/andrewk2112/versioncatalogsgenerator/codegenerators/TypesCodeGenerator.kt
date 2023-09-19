package io.github.andrewk2112.versioncatalogsgenerator.codegenerators

import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.values.LibraryValuesCodeGenerator
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.values.PluginValuesCodeGenerator
import org.intellij.lang.annotations.Language

/**
 * Generates source file code containing declarations of all basic reusable types used to describe catalogs' values.
 */
internal class TypesCodeGenerator(
    private val librariesCodeGenerator: LibraryValuesCodeGenerator,
    private val pluginsCodeGenerator:   PluginValuesCodeGenerator,
) {

    @Language("kotlin")
    internal fun generate(packageName: String, visibilityModifierPrefix: String): String = """
package $packageName

${librariesCodeGenerator.generateLibraryInterfaceCode(visibilityModifierPrefix)}

${pluginsCodeGenerator.generatePluginInterfaceCode(visibilityModifierPrefix)}

    """.trimIndent()

}
