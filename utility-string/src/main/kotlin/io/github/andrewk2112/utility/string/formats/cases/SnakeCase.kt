package io.github.andrewk2112.utility.string.formats.cases

import io.github.andrewk2112.utility.string.formats.WordsBasedFormat

/**
 * Regular **snake_case**.
 */
object SnakeCase : WordsBasedFormat() {

    override fun extractWords(source: String): List<String> = source.split('_')
                                                                    .filter { it.isNotEmpty() }

    override fun joinWords(words: List<String>): String = words.joinToString(separator = "_") { it.lowercase() }

}
