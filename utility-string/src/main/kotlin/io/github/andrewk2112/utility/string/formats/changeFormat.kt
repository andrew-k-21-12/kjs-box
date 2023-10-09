package io.github.andrewk2112.utility.string.formats

/**
 * Changes the format of source [String] according to the [from] and [to] configurations.
 */
fun String.changeFormat(from: Format, to: Format): String = to.postProcess(replace(from.divider, to.divider))
