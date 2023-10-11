package io.github.andrewk2112.utility.string.formats.cases

import io.github.andrewk2112.utility.string.extensions.decapitalize
import io.github.andrewk2112.utility.string.formats.WordsBasedFormat

/**
 * Lower **camelCase**.
 */
object LowerCamelCase : WordsBasedFormat() {

    override fun extractWords(source: String): List<String> = CamelCase.extractWords(source)

    override fun joinWords(words: List<String>): String = CamelCase.joinWords(words)

    override fun postProcess(source: String): String = source.decapitalize()

}
