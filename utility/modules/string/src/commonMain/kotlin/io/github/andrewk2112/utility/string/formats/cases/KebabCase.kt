package io.github.andrewk2112.utility.string.formats.cases

import io.github.andrewk2112.utility.string.formats.WordsBasedFormat

/**
 * Regular **kebab-case**.
 */
object KebabCase : WordsBasedFormat() {

    override fun extractWords(source: String): List<String> = source.split('-')
                                                                    .filter { it.isNotEmpty() }

    override fun joinWords(words: List<String>): String = words.joinToString(separator = "-") { it.lowercase() }

}
