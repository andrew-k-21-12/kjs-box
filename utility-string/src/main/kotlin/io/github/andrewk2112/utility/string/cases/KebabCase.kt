package io.github.andrewk2112.utility.string.cases

/**
 * **kebab-case** representations.
 */
object KebabCase : Case() {

    override fun extractWords(source: String): List<String> = source.split('-')
                                                                    .filter { it.isNotEmpty() }

    override fun joinWords(words: List<String>): String = words.joinToString(separator = "-") { it.lowercase() }

}
