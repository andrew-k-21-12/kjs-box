package io.github.andrewk2112.versioncatalogsgenerator.codegenerators.values

import io.github.andrewk2112.stringutility.cases.KebabCase
import io.github.andrewk2112.stringutility.cases.LowerCamelCase
import io.github.andrewk2112.stringutility.cases.changeCase
import io.github.andrewk2112.stringutility.indented
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.CodeEmitters
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.CommonCodeGeneration
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.ValuesCodeGenerator
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.ValuesCodeGenerator.GeneratedCode
import io.github.andrewk2112.versioncatalogsgenerator.extensions.toGeneratedCode
import io.github.andrewk2112.versioncatalogsgenerator.models.ParsedVersionCatalog
import io.github.andrewk2112.versioncatalogsgenerator.utility.Reference

/**
 * Generates the code for catalog's bundles.
 */
internal class BundleValuesCodeGenerator(private val codeGeneration: CommonCodeGeneration) : ValuesCodeGenerator {

    // Interface.

    override fun generateValuesCode(visibilityModifierPrefix: String, catalog: ParsedVersionCatalog): GeneratedCode? {
        val cachedPropertyName = Reference<String?>(null)
        return codeGeneration
            .generateCodeBlocks(
                catalog.bundles?.entries,
                { generateInterfaceCode(visibilityModifierPrefix, it, cachedPropertyName) },
                { generateImplementationCode(visibilityModifierPrefix, it, cachedPropertyName) }
            )
            ?.toGeneratedCode()
    }



    // Code generation.

    private fun generateInterfaceCode(
        visibilityModifierPrefix: String,
        codeEmitters: CodeEmitters<Map.Entry<String, List<String>>>,
        cachedPropertyName: Reference<String?>,
    ): String = """
${visibilityModifierPrefix}interface Bundles {${codeEmitters.emitCode {
        cachedPropertyName.value = it.key.changeCase(KebabCase, LowerCamelCase)
        "\n    val $cachedPropertyName: List<Library>"
    }}
}
    """.trimIndent()

    private fun generateImplementationCode(
        visibilityModifierPrefix: String,
        codeEmitters: CodeEmitters<Map.Entry<String, List<String>>>,
        cachedPropertyName: Reference<String?>
    ): String = """
${visibilityModifierPrefix}val bundles = object : Bundles {${codeEmitters.emitCode {
    """
    override val $cachedPropertyName = listOf(
${"        " indented it.value.generateListElements()}
    )
    """.trimEnd()
    }}
}
    """.trimIndent()

    private fun List<String>.generateListElements(): String =
        joinToString(separator = ",\n") { "libraries.${it.changeCase(KebabCase, LowerCamelCase)}" }

}
