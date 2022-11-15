package io.github.andrewk2112.extensions

import csstype.ClassName

/**
 * Simplifies string array conversions to [ClassName] for internal needs.
 * */
@PublishedApi
internal fun Array<out String>.toClassName() = ClassName(joinToString(separator = " "))
