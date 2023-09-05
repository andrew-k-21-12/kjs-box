package io.github.andrewk2112.versioncatalogsgenerator.codegenerators

import io.github.andrewk2112.versioncatalogsgenerator.extensions.indented
import io.github.andrewk2112.versioncatalogsgenerator.models.ParsedVersionCatalog
import org.intellij.lang.annotations.Language

/**
 * Generates the overall version catalog code by joining the outputs of provided [codeGenerators].
 */
internal class VersionCatalogCodeGenerator(private vararg val codeGenerators: ValuesCodeGenerator) {

    internal fun generate(packageName: String, catalogName: String, catalogData: ParsedVersionCatalog): String {
        val interfacesCode      = StringBuilder()
        val implementationsCode = StringBuilder()
        codeGenerators.forEach { codeGenerator ->
            codeGenerator.generateValuesCode(catalogData)?.run {
                interfacesCode.appendLine(interfaceCode).appendLine()
                implementationsCode.appendLine(implementationPropertyCode).appendLine()
            }
        }
        return bindVersionCatalogCodeParts(
            packageName,
            catalogName,
            interfacesCode.trimEnd().toString(),
            implementationsCode.trimEnd().toString()
        )
    }

    @Language("kotlin")
    private fun bindVersionCatalogCodeParts(
        packageName: String,
        catalogName: String,
        interfacesCode: String,
        implementationsCode: String
    ): String = """
package $packageName

internal class $catalogName {

${"    " indented interfacesCode}

${"    " indented implementationsCode}

}

    """.trimIndent()

}
