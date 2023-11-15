package io.github.andrewk2112.kjsbox.frontend.core.utility.date

import kotlin.js.Date

/**
 * Formats JS [Date]s.
 */
interface DateFormat {

    /**
     * Formats the provided [date] as [String] according to the specified [language] (locale).
     */
    fun format(date: Date, language: String): String

}
