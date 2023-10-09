package io.github.andrewk2112.utility.string.cases

import io.github.andrewk2112.utility.string.decapitalize

/**
 * Lower **camelCase** representations.
 */
object LowerCamelCase : Case() {

    override fun extractWords(source: String): List<String> = CamelCase.extractWords(source)

    override fun joinWords(words: List<String>): String = CamelCase.joinWords(words).decapitalize()

}
