package io.github.andrewk2112.extensions

/**
 * Checks if all chars in this string are uppercase.
 * */
fun String.isUppercase(): Boolean = all { it.isUpperCase() }

/**
 * Capitalizes only the first char and converts the rest ones to lowercase.
 * */
fun String.camelCaseWord(): String = lowercase().replaceFirstChar { it.titlecase() }

/**
 * Converts kebab or snake cases into the camel case.
 * */
fun String.camelCase(): String = split("-", "_").let { parts ->
    if (parts.size <= 1) {
        if (isUppercase()) lowercase() else this
    } else {
        parts.joinToString(separator = "") { it.camelCaseWord() }
    }
}

/**
 * Converts kebab or snake cases into the lower camel case.
 * */
fun String.lowerCamelCase(): String = camelCase().replaceFirstChar {
    if (it.isUpperCase()) it.lowercase() else it.toString()
}
