package io.github.andrewk2112.versioncatalogsgenerator.codegenerators.values

import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.ValuesCodeGenerator
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.ValuesCodeGenerator.GeneratedCode
import io.github.andrewk2112.versioncatalogsgenerator.extensions.indented
import io.github.andrewk2112.versioncatalogsgenerator.models.ParsedVersionCatalog
import org.intellij.lang.annotations.Language

/**
 * Generates the code for catalog's versions.
 */
internal class VersionValuesCodeGenerator(
    private val codeGeneration: SharedValuesCodeGeneration
) : ValuesCodeGenerator {

    // Interface.

    override fun generateValuesCode(catalog: ParsedVersionCatalog): GeneratedCode? =
        codeGeneration.generate(
            catalog.versions,
            ::interfaceProperty,
            ::implementationProperty,
            ::overallInterface,
            ::overallImplementation
        )



    // Code generation.

    @Language("kotlin")
    private fun interfaceProperty(propertyName: String): String = """
val $propertyName: String
    """.trimIndent()

    @Language("kotlin")
    private fun implementationProperty(propertyName: String, value: String): String = """
override val $propertyName = "$value"
    """.trimIndent()

    @Language("kotlin")
    private fun overallInterface(propertiesCode: String): String = """
internal interface Versions {
${"    " indented propertiesCode}
}
    """.trimIndent()

    @Language("kotlin")
    private fun overallImplementation(propertiesCode: String): String = """
internal val versions = object : Versions {
${"    " indented propertiesCode}
}
    """.trimIndent()

}
