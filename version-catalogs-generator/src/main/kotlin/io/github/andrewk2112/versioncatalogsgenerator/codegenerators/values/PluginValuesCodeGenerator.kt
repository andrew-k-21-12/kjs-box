package io.github.andrewk2112.versioncatalogsgenerator.codegenerators.values

import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.ValuesCodeGenerator
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.ValuesCodeGenerator.GeneratedCode
import io.github.andrewk2112.versioncatalogsgenerator.extensions.indented
import io.github.andrewk2112.versioncatalogsgenerator.models.ParsedVersionCatalog
import org.intellij.lang.annotations.Language

/**
 * Generates the code for catalog's plugins.
 */
internal class PluginValuesCodeGenerator(
    private val codeGeneration: SharedValuesCodeGeneration
) : ValuesCodeGenerator {

    // Interface.

    @Language("kotlin")
    internal fun generatePluginInterfaceCode(): String = """
internal interface Plugin {
    val id: String
    val version: String?
}
    """.trimIndent()

    override fun generateValuesCode(catalog: ParsedVersionCatalog): GeneratedCode? =
        codeGeneration.generate(
            catalog.plugins,
            ::interfaceProperty,
            ::implementationProperty,
            ::overallInterface,
            ::overallImplementation
        )



    // Code generation.

    @Language("kotlin")
    private fun interfaceProperty(propertyName: String): String = """
val $propertyName: Plugin
    """.trimIndent()

    @Language("kotlin")
    private fun implementationProperty(propertyName: String, value: ParsedVersionCatalog.Plugin): String = """
override val $propertyName = object : Plugin {
    override val id = "${value.id}"
    override val version = ${codeGeneration.escape(value.version)}
}
    """.trimIndent()

    @Language("kotlin")
    private fun overallInterface(propertiesCode: String): String = """
internal interface Plugins {
${"    " indented propertiesCode}
}
    """.trimIndent()

    @Language("kotlin")
    private fun overallImplementation(propertiesCode: String): String = """
internal val plugins = object : Plugins {
${"    " indented propertiesCode}
}
    """.trimIndent()

}
