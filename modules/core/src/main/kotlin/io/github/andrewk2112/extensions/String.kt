package io.github.andrewk2112.extensions

/**
 * Capitalizes only the first char and converts the rest ones to lowercase.
 */
fun String.camelCaseWord(): String = lowercase().replaceFirstChar { it.titlecase() }

/**
 * Converts kebab or snake cases into the camel case.
 */
fun String.camelCaseFromKebabOrSnakeCase(): String = split("-", "_").let { parts ->
    if (parts.size <= 1) {
        if (isUppercase()) lowercase() else this
    } else {
        parts.joinToString(separator = "") { it.camelCaseWord() }
    }
}

/**
 * Capitalizes the first char.
 */
fun String.capitalize(): String = replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

/**
 * Checks if all chars in this string are uppercase.
 */
fun String.isUppercase(): Boolean = all { it.isUpperCase() }

/**
 * Converts kebab or snake cases into the lower camel case.
 */
fun String.lowerCamelCaseFromKebabOrSnakeCase(): String = camelCaseFromKebabOrSnakeCase().replaceFirstChar {
    if (it.isUpperCase()) it.lowercase() else it.toString()
}
