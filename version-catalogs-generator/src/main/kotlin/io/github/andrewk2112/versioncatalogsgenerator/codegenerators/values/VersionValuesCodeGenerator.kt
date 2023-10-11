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
 * Generates the code for catalog's versions.
 */
internal class VersionValuesCodeGenerator(private val codeGeneration: CommonCodeGeneration) : ValuesCodeGenerator {

    // Interface.

    override fun generateValuesCode(visibilityModifierPrefix: String, catalog: ParsedVersionCatalog): GeneratedCode? {
        val cachedPropertyName = Reference<String?>(null)
        return codeGeneration
            .generateCodeBlocks(
                catalog.versions?.entries,
                { generateInterfaceCode(visibilityModifierPrefix, it, cachedPropertyName) },
                { generateImplementationCode(visibilityModifierPrefix, it, cachedPropertyName) }
            )
            ?.toGeneratedCode()
    }



    // Code generation.

    private fun generateInterfaceCode(
        visibilityModifierPrefix: String,
        codeEmitters: CodeEmitters<Map.Entry<String, String>>,
        cachedPropertyName: Reference<String?>
    ): String = """
${visibilityModifierPrefix}interface Versions {${codeEmitters.emitCode { 
    cachedPropertyName.value = it.key.changeFormat(KebabCase, LowerCamelCase)
    "\n    val $cachedPropertyName: String"
}}
}
    """.trimIndent()

    private fun generateImplementationCode(
        visibilityModifierPrefix: String,
        codeEmitters: CodeEmitters<Map.Entry<String, String>>,
        cachedPropertyName: Reference<String?>
    ): String = """
${visibilityModifierPrefix}val versions = object : Versions {${codeEmitters.emitCode {
    "\n    override val $cachedPropertyName = ${codeGeneration.escape(it.value)}"
}}
}
    """.trimIndent()

}
