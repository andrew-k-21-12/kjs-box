package io.github.andrewk2112.stringutility.cases

/**
 * Provides contracts to convert from and to various [String] representation cases.
 */
abstract class Case internal constructor() {

    /**
     * Splits a [source] string into its component words.
     */
    internal abstract fun extractWords(source: String): List<String>

    /**
     * Joins [words] into a particular representation.
     */
    internal abstract fun joinWords(words: List<String>): String

}
