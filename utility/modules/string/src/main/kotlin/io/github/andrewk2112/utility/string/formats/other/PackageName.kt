package io.github.andrewk2112.utility.string.formats.other

import io.github.andrewk2112.utility.string.formats.DividerBasedFormat

/**
 * Formats [String]s as packages: no uppercase letters and dashes, dots are used as dividers.
 */
object PackageName : DividerBasedFormat() {

    override fun postProcess(source: String): String = source.replace("-", "").lowercase()

    override val divider: String = "."

}
