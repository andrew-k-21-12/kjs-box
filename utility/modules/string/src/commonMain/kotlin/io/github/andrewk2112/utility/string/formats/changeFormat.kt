package io.github.andrewk2112.utility.string.formats

// Public.

/**
 * Changes the format of a source [String] according to [from] and [to] configurations.
 */
fun String.changeFormat(from: Format, to: Format): String =
    to.postProcess(
        when (from) {
            is DividerBasedFormat -> changeFormat(from, to)
            is WordsBasedFormat   -> changeFormat(from, to)
        }
    )



// Private.

private fun String.changeFormat(from: DividerBasedFormat, to: Format): String =
    when (to) {
        is DividerBasedFormat -> replace(from.divider, to.divider)
        is WordsBasedFormat   -> to.joinWords(split(from.divider))
    }

private fun String.changeFormat(from: WordsBasedFormat, to: Format): String =
    when (to) {
        is DividerBasedFormat -> from.extractWords(this).joinToString(to.divider)
        is WordsBasedFormat   -> to.joinWords(from.extractWords(this))
    }
