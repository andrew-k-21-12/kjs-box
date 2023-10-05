package io.github.andrewk2112.versioncatalogsgenerator.codegenerators

import io.github.andrewk2112.stringutility.indented
import io.github.andrewk2112.versioncatalogsgenerator.models.ParsedVersionCatalog

/**
 * Generates the overall version catalog code by joining the outputs of provided [codeGenerators].
 */
internal class VersionCatalogCodeGenerator(private vararg val codeGenerators: ValuesCodeGenerator) {

    internal fun generate(
        packageName: String,
        visibilityModifierPrefix: String,
        catalogName: String,
        catalogData: ParsedVersionCatalog
    ): String {
        val interfacesCode      = StringBuilder()
        val implementationsCode = StringBuilder()
        codeGenerators.forEach { codeGenerator ->
            codeGenerator.generateValuesCode(visibilityModifierPrefix, catalogData)?.run {
                interfacesCode.appendLine(interfaceCode).appendLine()
                implementationsCode.appendLine(implementationPropertyCode).appendLine()
            }
        }
        return bindVersionCatalogCodeParts(
            packageName,
            visibilityModifierPrefix,
            catalogName,
            interfacesCode.trimEnd().toString(),
            implementationsCode.trimEnd().toString()
        )
    }

    private fun bindVersionCatalogCodeParts(
        packageName: String,
        visibilityModifierPrefix: String,
        catalogName: String,
        interfacesCode: String,
        implementationsCode: String
    ): String = """
package $packageName

${visibilityModifierPrefix}class $catalogName {

${"    " indented interfacesCode}

${"    " indented implementationsCode}

}

    """.trimIndent()

}
