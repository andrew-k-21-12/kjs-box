package io.github.andrewk2112.stringutility.formats

/**
 * Formats [String]s as paths: slashes are used as dividers, no other modifications.
 */
object PathFormat : Format() {

    override fun postProcess(source: String): String = source

    override val divider: String = "/"

}
