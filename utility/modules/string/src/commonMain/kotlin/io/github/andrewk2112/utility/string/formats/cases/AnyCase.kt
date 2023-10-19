package io.github.andrewk2112.utility.string.formats.cases

import io.github.andrewk2112.utility.string.formats.Format
import io.github.andrewk2112.utility.string.formats.changeFormat

/** To make [changeFormat] convert a [String] of any case to a required [Format]. */
val AnyCase: Array<Format> = arrayOf(CamelCase, KebabCase, SnakeCase)
