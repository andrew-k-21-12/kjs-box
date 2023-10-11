package io.github.andrewk2112.utility.string.formats.other

import io.github.andrewk2112.utility.string.formats.DividerBasedFormat

/**
 * Formats [String]s as universal paths: slashes are used as dividers, no other modifications.
 */
object UniversalPath : DividerBasedFormat() {

    override val divider: String = "/"

}
