package io.github.andrewk2112.utility.string.formats

/**
 * Describes a contract to change [String] formats.
 */
abstract class Format internal constructor() {

    /**
     * How to process a [source] string after replacing of its [divider]s.
     */
    internal abstract fun postProcess(source: String): String

    /** A divider to be used in this [Format]. */
    internal abstract val divider: String

}
