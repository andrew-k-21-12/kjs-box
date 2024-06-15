package io.github.andrewk2112.kjsbox.frontend.example.sharedutility

import io.github.andrewk2112.utility.js.utility.dateTimeFormatOptions
import js.intl.DayFormat
import js.intl.MonthFormat
import js.intl.YearFormat

object DateTimeFormatOptions {

    /** Dates without time having a format similar to the following: *December 14, 2020* */
    val LONG_DATE = dateTimeFormatOptions {
        day   = DayFormat.numeric
        month = MonthFormat.long
        year  = YearFormat.numeric
    }

}
