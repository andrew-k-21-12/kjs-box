package io.github.andrewk2112.utility.string.formats.cases

import io.github.andrewk2112.utility.string.extensions.capitalize
import io.github.andrewk2112.utility.string.formats.WordsBasedFormat

/**
 * Regular **CamelCase**.
 */
object CamelCase : WordsBasedFormat() {

    override fun extractWords(source: String): List<String> = source.split(capitalizedWordsExtractingRegex)
                                                                    .filter { it.isNotEmpty() }

    override fun joinWords(words: List<String>): String =
        words.joinToString(separator = "") { it.lowercase().capitalize() }

    private val capitalizedWordsExtractingRegex = Regex("(?=\\p{Lu})")

}
