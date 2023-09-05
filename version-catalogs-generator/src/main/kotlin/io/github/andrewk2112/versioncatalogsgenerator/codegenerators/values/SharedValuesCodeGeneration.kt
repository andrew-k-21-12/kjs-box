package io.github.andrewk2112.versioncatalogsgenerator.codegenerators.values

import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.ValuesCodeGenerator
import io.github.andrewk2112.versioncatalogsgenerator.codegenerators.ValuesCodeGenerator.GeneratedCode
import io.github.andrewk2112.versioncatalogsgenerator.extensions.lowerCamelCaseFromKebabOrSnakeCase

/**
 * Reusable logic for a family of [ValuesCodeGenerator]s to prefer a composition over inheritance.
 */
internal class SharedValuesCodeGeneration {

    internal fun <T> generate(
        sourceValues:                             Map<String, T>?,
        interfacePropertyGenerator:               (propertyName: String) -> String,
        implementationPropertyGenerator:          (propertyName: String, value: T) -> String,
        interfaceGenerator:                       (propertiesCode: String) -> String,
        interfaceImplementationPropertyGenerator: (propertiesCode: String) -> String
    ): GeneratedCode? {
        if (sourceValues.isNullOrEmpty()) return null
        val interfaceProperties      = StringBuilder()
        val implementationProperties = StringBuilder()
        sourceValues.forEach { (name, value) ->
            val propertyName = name.lowerCamelCaseFromKebabOrSnakeCase()
            interfaceProperties.appendLine(interfacePropertyGenerator(propertyName))
            implementationProperties.appendLine(implementationPropertyGenerator(propertyName, value))
        }
        return GeneratedCode(
            interfaceGenerator(interfaceProperties.trimEnd().toString()),
            interfaceImplementationPropertyGenerator(implementationProperties.trimEnd().toString())
        )
    }

    /**
     * Provides correct code representation for a nullable [string].
     */
    internal fun escape(string: String?): String = string?.let { "\"$it\"" } ?: "null"

}
