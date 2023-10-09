package io.github.andrewk2112.utility.string.formats

/**
 * Formats [String]s as packages: no uppercase letters and dashes, dots are used as dividers.
 */
object PackageFormat : Format() {

    override fun postProcess(source: String): String = source.replace("-", "").lowercase()

    override val divider: String = "."

}
