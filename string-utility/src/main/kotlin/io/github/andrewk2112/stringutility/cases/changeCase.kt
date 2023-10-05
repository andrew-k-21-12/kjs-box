package io.github.andrewk2112.stringutility.cases

/**
 * Changes the representation of source [String] according to the [from] and [to] configurations.
 */
fun String.changeCase(from: Case, to: Case): String = to.joinWords(from.extractWords(this))
