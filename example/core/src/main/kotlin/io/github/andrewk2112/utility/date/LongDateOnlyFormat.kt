package io.github.andrewk2112.utility.date

import kotlin.js.Date

/**
 * Date-only format with a fully written month
 * but without any other information such as for example a week day's name.
 */
class LongDateOnlyFormat : DateFormat {

    override fun format(date: Date, language: String): String = date.toLocaleDateString(language, localeOptions)

    /** The platform-specific [Date.LocaleOptions] describing the exact format to be applied. */
    private val localeOptions: Date.LocaleOptions = dateLocaleOptions {
        day   = "numeric"
        month = "long"
        year  = "numeric"
    }

}
