package io.github.andrewk2112.versioncatalogsgenerator.codegenerators.values

import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.CodeEmitters
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.CommonCodeGeneration
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.ValuesCodeGenerator
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.ValuesCodeGenerator.GeneratedCode
import io.github.andrewk2112.versioncatalogsgenerator.extensions.lowerCamelCaseFromKebabOrSnakeCase
import io.github.andrewk2112.versioncatalogsgenerator.extensions.toGeneratedCode
import io.github.andrewk2112.versioncatalogsgenerator.models.ParsedVersionCatalog
import io.github.andrewk2112.versioncatalogsgenerator.utility.Reference

/**
 * Generates the code for catalog's plugins.
 */
internal class PluginValuesCodeGenerator(private val codeGeneration: CommonCodeGeneration) : ValuesCodeGenerator {

    // Interface.

    internal fun generatePluginInterfaceCode(visibilityModifierPrefix: String): String = """
${visibilityModifierPrefix}interface Plugin {
    val id: String
    val version: String?
}
    """.trimIndent()

    override fun generateValuesCode(visibilityModifierPrefix: String, catalog: ParsedVersionCatalog): GeneratedCode? {
        val cachedPropertyName = Reference<String?>(null)
        return codeGeneration
            .generateCodeBlocks(
                catalog.plugins?.entries,
                { generateInterfaceCode(visibilityModifierPrefix, it, cachedPropertyName) },
                { generateImplementationCode(visibilityModifierPrefix, it, cachedPropertyName) }
            )
            ?.toGeneratedCode()
    }



    // Code generation.

    private fun generateInterfaceCode(
        visibilityModifierPrefix: String,
        codeEmitters: CodeEmitters<Map.Entry<String, ParsedVersionCatalog.Plugin>>,
        cachedPropertyName: Reference<String?>,
    ): String = """
${visibilityModifierPrefix}interface Plugins {${codeEmitters.emitCode { 
    cachedPropertyName.value = it.key.lowerCamelCaseFromKebabOrSnakeCase()
    "\n    val $cachedPropertyName: Plugin"
}}
}
    """.trimIndent()

    private fun generateImplementationCode(
        visibilityModifierPrefix: String,
        codeEmitters: CodeEmitters<Map.Entry<String, ParsedVersionCatalog.Plugin>>,
        cachedPropertyName: Reference<String?>
    ): String = """
${visibilityModifierPrefix}val plugins = object : Plugins {${codeEmitters.emitCode {
    """
    override val $cachedPropertyName = object : Plugin {
        override val id = "${it.value.id}"
        override val version = ${codeGeneration.escape(it.value.version)}
    }
    """.trimEnd()
}}
}
    """.trimIndent()

}
