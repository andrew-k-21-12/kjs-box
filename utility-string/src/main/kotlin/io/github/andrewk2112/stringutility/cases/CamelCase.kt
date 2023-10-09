package io.github.andrewk2112.stringutility.cases

import io.github.andrewk2112.stringutility.capitalize

/**
 * Regular **CamelCase** representations.
 */
object CamelCase : Case() {

    override fun extractWords(source: String): List<String> = source.split(capitalizedWordsExtractingRegex)
                                                                    .filter { it.isNotEmpty() }

    override fun joinWords(words: List<String>): String =
        words.joinToString(separator = "") { it.lowercase().capitalize() }

    private val capitalizedWordsExtractingRegex = Regex("(?=\\p{Lu})")

}
