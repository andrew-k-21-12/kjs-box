package io.github.andrewk2112.versioncatalogsgenerator.codegenerators.values

import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.ValuesCodeGenerator
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.ValuesCodeGenerator.GeneratedCode
import io.github.andrewk2112.versioncatalogsgenerator.extensions.indented
import io.github.andrewk2112.versioncatalogsgenerator.models.ParsedVersionCatalog
import org.intellij.lang.annotations.Language

/**
 * Generates the code for catalog's libraries.
 */
internal class LibraryValuesCodeGenerator(
    private val codeGeneration: SharedValuesCodeGeneration
) : ValuesCodeGenerator {

    // Interface.

    // @Language("kotlin") - can't keep it enabled as the dollar signs are getting highlighted as errors
    internal fun generateLibraryInterfaceCode(): String = """
internal abstract class Library {

    internal abstract val group: String
    internal abstract val name: String
    internal abstract val version: String?

    internal val fullNotation: String
        get() = "${"$"}group:${"$"}name${"$"}{version?.let { ":${"$"}it" } ?: ""}"

}
    """.trimIndent()

    override fun generateValuesCode(catalog: ParsedVersionCatalog): GeneratedCode? =
        codeGeneration.generate(
            catalog.libraries,
            ::interfaceProperty,
            ::implementationProperty,
            ::overallInterface,
            ::overallImplementation
        )



    // Code generation.

    @Language("kotlin")
    private fun interfaceProperty(propertyName: String): String = """
val $propertyName: Library
    """.trimIndent()

    @Language("kotlin")
    private fun implementationProperty(propertyName: String, value: ParsedVersionCatalog.Library): String = """
override val $propertyName = object : Library() {
    override val group = "${value.group}"
    override val name = "${value.name}"
    override val version = ${codeGeneration.escape(value.version)}
}
    """.trimIndent()

    @Language("kotlin")
    private fun overallInterface(propertiesCode: String): String = """
internal interface Libraries {
${"    " indented propertiesCode}
}
    """.trimIndent()

    @Language("kotlin")
    private fun overallImplementation(propertiesCode: String): String = """
internal val libraries = object : Libraries {
${"    " indented propertiesCode}
}
    """.trimIndent()

}
