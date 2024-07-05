package io.github.andrewk2112.utility.js.extensions

/**
 * Removes all possible white spaces and new lines in the provided [json].
 */
@Suppress("UnusedReceiverParameter")
fun JSON.minify(json: String): String = json.replace(jsonMinifyingRegex, "")

/** Removes all possible white spaces and new lines in JSONs. */
private val jsonMinifyingRegex = Regex("\\s(?=([^\"]*\"[^\"]*\")*[^\"]*$)")
