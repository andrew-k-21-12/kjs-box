package io.github.andrewk2112.versioncatalogsgenerator.extensions

// This file shouldn't exist and must be extracted into a shared module.

/**
 * Converts kebab or snake cases into the camel case.
 */
internal fun String.camelCaseFromKebabOrSnakeCase(): String = split("-", "_").let { parts ->
    if (parts.size <= 1) {
        if (isUppercase()) lowercase() else this
    } else {
        parts.joinToString(separator = "") { it.camelCaseWord() }
    }
}

/**
 * Capitalizes only the first char and converts the rest ones to lowercase.
 */
internal fun String.camelCaseWord(): String = lowercase().replaceFirstChar { it.titlecase() }

/**
 * Replaces all dots in the [String] with slashes.
 */
internal fun String.dotsToSlashes(): String = replace(".", "/")

/**
 * Syntax sugar for [prependIndent]: prepends an indent (the left side argument) for each line of the [source] string.
 */
internal infix fun String.indented(source: String): String = source.prependIndent(this)

/**
 * Checks if all chars in this string are uppercase.
 */
internal fun String.isUppercase(): Boolean = all { it.isUpperCase() }

/**
 * Converts kebab or snake cases into the lower camel case.
 */
internal fun String.lowerCamelCaseFromKebabOrSnakeCase(): String = camelCaseFromKebabOrSnakeCase().replaceFirstChar {
    if (it.isUpperCase()) it.lowercase() else it.toString()
}
