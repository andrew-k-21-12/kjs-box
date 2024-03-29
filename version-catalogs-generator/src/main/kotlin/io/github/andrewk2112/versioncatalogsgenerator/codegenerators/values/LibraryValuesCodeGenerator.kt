package io.github.andrewk2112.versioncatalogsgenerator.codegenerators.values

import io.github.andrewk2112.utility.common.utility.Reference
import io.github.andrewk2112.utility.string.formats.cases.KebabCase
import io.github.andrewk2112.utility.string.formats.cases.LowerCamelCase
import io.github.andrewk2112.utility.string.formats.changeFormat
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.CodeEmitters
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.CommonCodeGeneration
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.values.ValuesCodeGenerator.GeneratedCode
import io.github.andrewk2112.versioncatalogsgenerator.extensions.toGeneratedCode
import io.github.andrewk2112.versioncatalogsgenerator.models.ParsedVersionCatalog

/**
 * Generates the code for catalog's libraries.
 */
internal class LibraryValuesCodeGenerator(private val codeGeneration: CommonCodeGeneration) : ValuesCodeGenerator {

    // Interface.

    internal fun generateLibraryInterfaceCode(visibilityModifierPrefix: String): String = """
${visibilityModifierPrefix}abstract class Library {

    ${visibilityModifierPrefix}abstract val group: String
    ${visibilityModifierPrefix}abstract val name: String
    ${visibilityModifierPrefix}abstract val version: String?

    ${visibilityModifierPrefix}val fullNotation: String
        get() = "${"$"}group:${"$"}name${"$"}{version?.let { ":${"$"}it" } ?: ""}"

}
    """.trimIndent()

    override fun generateValuesCode(visibilityModifierPrefix: String, catalog: ParsedVersionCatalog): GeneratedCode? {
        val cachedPropertyName = Reference<String?>(null)
        return codeGeneration
            .generateCodeBlocks(
                catalog.libraries?.entries,
                { generateInterfaceCode(visibilityModifierPrefix, it, cachedPropertyName) },
                { generateImplementationCode(visibilityModifierPrefix, it, cachedPropertyName) }
            )
            ?.toGeneratedCode()
    }



    // Code generation.

    private fun generateInterfaceCode(
        visibilityModifierPrefix: String,
        codeEmitters: CodeEmitters<Map.Entry<String, ParsedVersionCatalog.Library>>,
        cachedPropertyName: Reference<String?>,
    ): String = """
${visibilityModifierPrefix}interface Libraries {${codeEmitters.emitCode { 
    cachedPropertyName.value = it.key.changeFormat(KebabCase, LowerCamelCase)
    "\n    val $cachedPropertyName: Library"
}}
}
    """.trimIndent()

    private fun generateImplementationCode(
        visibilityModifierPrefix: String,
        codeEmitters: CodeEmitters<Map.Entry<String, ParsedVersionCatalog.Library>>,
        cachedPropertyName: Reference<String?>
    ): String = """
${visibilityModifierPrefix}val libraries = object : Libraries {${codeEmitters.emitCode {
    """
    override val $cachedPropertyName = object : Library() {
        override val group = "${it.value.group}"
        override val name = "${it.value.name}"
        override val version = ${codeGeneration.escape(it.value.version)}
    }
    """.trimEnd() 
}}
}
    """.trimIndent()

}
