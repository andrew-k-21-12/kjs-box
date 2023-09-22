package io.github.andrewk2112.kjsbox.frontend.core.extensions

import web.cssom.ClassName

/**
 * Simplifies string array conversions to [ClassName] for internal needs.
 */
@PublishedApi
internal fun Array<out String>.toClassName() = ClassName(joinToString(separator = " "))
