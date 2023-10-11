package io.github.andrewk2112.utility.string.formats

/**
 * Base contract describing families of possible format variants for [String]s.
 */
sealed class Format {

    /**
     * An operation to be applied to a result [String] after all basic transformations have been done.
     *
     * Does nothing by default.
     */
    internal open fun postProcess(source: String): String = source

}

/**
 * Transforms [String]s by replacing its [divider]s or preliminary splitting them into words.
 *
 * Faster than [WordsBasedFormat] but provides less flexibility.
 */
abstract class DividerBasedFormat internal constructor() : Format() {

    /** A target divider to be searched and processed in provided [String]s. */
    internal abstract val divider: String

}

/**
 * Transforms [String]s by splitting them into words and joining these words afterward.
 *
 * Slower than [DividerBasedFormat] but provides more flexibility.
 */
abstract class WordsBasedFormat internal constructor() : Format() {

    /**
     * How to extract words from a provided [source] string.
     */
    internal abstract fun extractWords(source: String): List<String>

    /**
     * How to process and concatenate [words] into a result [String].
     */
    internal abstract fun joinWords(words: List<String>): String

}
