package io.github.andrewk2112.utility.string.formats

import kotlin.jvm.JvmName

// Public.

/**
 * Changes the format of a source [String] according to [from] and [to] configurations.
 */
fun String.changeFormat(from: Format, to: Format): String =
    to.postProcess(
        when (from) {
            is DividerBasedFormat -> when (to) {
                                         is DividerBasedFormat -> replace(from.divider, to.divider)
                                         is WordsBasedFormat   -> to.joinWords(split(from.divider))
                                     }
            is WordsBasedFormat   -> from.extractWords(this).joinWords(to)
        }
    )

/**
 * Changes the format of a source [String] according to all [from] and [to] configurations.
 */
fun String.changeFormat(vararg from: Format, to: Format): String = changeFormat(from, to)

/**
 * Changes the format of a source [String] according to all [from] and [to] configurations.
 */
@JvmName("combinedChangeFormat")
fun String.changeFormat(from: Array<out Format>, to: Format): String = to.postProcess(extractWords(from).joinWords(to))



// Private.

/**
 * Extracts all words from a source [String] by using the rules provided by the [formats].
 */
private fun String.extractWords(formats: Array<out Format>): List<String> {

    // Initializing with the only word which is the original string.
    val words = mutableListOf(this)

    // For each format trying to split every available word further while keeping the words' order.
    for (format in formats) {
        repeat(words.size) {

            // Popping the first word, trying to split it further and adding its new split parts to the tail.
            val poppedWord = words.removeFirst()
            words.addAll(
                when (format) {
                    is DividerBasedFormat -> poppedWord.split(format.divider)
                    is WordsBasedFormat   -> format.extractWords(poppedWord)
                }
            )

        }
    }

    return words

}

/**
 * Joins all provided words into a single [String] according to the rules of provided [format].
 */
private fun List<String>.joinWords(format: Format): String =
    when (format) {
        is DividerBasedFormat -> joinToString(format.divider)
        is WordsBasedFormat   -> format.joinWords(this)
    }
