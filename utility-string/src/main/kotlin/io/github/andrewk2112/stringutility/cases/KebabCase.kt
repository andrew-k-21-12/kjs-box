package io.github.andrewk2112.stringutility.cases

/**
 * **kebab-case** representations.
 */
object KebabCase : Case() {

    override fun extractWords(source: String): List<String> = source.split('-')
                                                                    .filter { it.isNotEmpty() }

    override fun joinWords(words: List<String>): String = words.joinToString(separator = "-") { it.lowercase() }

}
