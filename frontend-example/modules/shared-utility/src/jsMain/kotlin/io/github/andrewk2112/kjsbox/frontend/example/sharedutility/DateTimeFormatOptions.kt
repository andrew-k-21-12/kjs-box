package io.github.andrewk2112.kjsbox.frontend.example.sharedutility

import io.github.andrewk2112.utility.js.utility.dateTimeFormatOptions

object DateTimeFormatOptions {

    /** Dates without time having a format similar to the following: *December 14, 2020* */
    val LONG_DATE = dateTimeFormatOptions {
        day   = "numeric"
        month = "long"
        year  = "numeric"
    }

}
