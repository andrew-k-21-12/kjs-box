package io.github.andrewk2112.utility

import kotlin.js.Date

/**
 * Formats JS [Date]s.
 *
 * In the case of intensive usage from lots of places should be reorganized into some static version.
 */
class DateFormat {

    // Utility.

    /**
     * States all available formats can be applied.
     */
    sealed class Options(val localeOptions: Date.LocaleOptions) {

        /**
         * Date-only format with a fully written month
         * but without any other information such as for example a week day's name.
         */
        class LongDate : Options(dateLocaleOptions {
            day   = "numeric"
            month = "long"
            year  = "numeric"
        })

    }



    // Public.

    /**
     * Formats the provided [date] as [String] according to the specified [language] (locale) and [options].
     */
    fun format(date: Date, language: String, options: Options): String =
        date.toLocaleDateString(language, options.localeOptions)

}
