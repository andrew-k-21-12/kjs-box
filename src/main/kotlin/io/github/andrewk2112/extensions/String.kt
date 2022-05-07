package io.github.andrewk2112.extensions

/**
 * Capitalizes only the first char and converts the rest ones to lowercase.
 * */
fun String.camelCaseWord(): String =
    this.lowercase()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
