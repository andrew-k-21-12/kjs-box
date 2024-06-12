package io.github.andrewk2112.utility.string.formats.cases

import io.github.andrewk2112.utility.string.formats.WordsBasedFormat

/**
 * Screaming **SNAKE_CASE**.
 */
object ScreamingSnakeCase : WordsBasedFormat() {

    override fun extractWords(source: String): List<String> = SnakeCase.extractWords(source)

    override fun joinWords(words: List<String>): String = words.joinToString(separator = "_") { it.uppercase() }

}
