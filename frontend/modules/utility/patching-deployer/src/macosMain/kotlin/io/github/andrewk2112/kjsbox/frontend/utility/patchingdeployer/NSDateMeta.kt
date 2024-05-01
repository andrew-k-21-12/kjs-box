package io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer

import platform.Foundation.NSDate
import platform.Foundation.NSDateMeta
import platform.Foundation.dateWithTimeIntervalSince1970

/**
 * Creates an [NSDate] object from regular UNIX [timeMillis].
 *
 * Implementation note - for some reason there is a 1-second difference
 * between regular UNIX time and the one accepted as an argument for [NSDateMeta.dateWithTimeIntervalSince1970]:
 * this is why this subtraction with `1_000` is done.
 */
internal fun NSDateMeta.dateWithUnixTime(timeMillis: Long): NSDate =
    dateWithTimeIntervalSince1970((timeMillis - 1_000) / 1_000.0)
