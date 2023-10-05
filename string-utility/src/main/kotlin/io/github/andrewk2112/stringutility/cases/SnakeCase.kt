package io.github.andrewk2112.stringutility.cases

/**
 * **snake_case** representations.
 */
object SnakeCase : Case() {

    override fun extractWords(source: String): List<String> = source.split('_')
                                                                    .filter { it.isNotEmpty() }

    override fun joinWords(words: List<String>): String = words.joinToString(separator = "_") { it.lowercase() }

}
