package io.github.andrewk2112.utility.js.utility

import js.intl.DateTimeFormatOptions

/**
 * Creates a new JS object allowing to apply type-safe [DateTimeFormatOptions] to it.
 */
inline fun dateTimeFormatOptions(init: DateTimeFormatOptions.() -> Unit): DateTimeFormatOptions =
    js("new Object()").unsafeCast<DateTimeFormatOptions>().apply(init)
