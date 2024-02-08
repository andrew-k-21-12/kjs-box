package io.github.andrewk2112.kjsbox.frontend.example.spacexcrew.data

import io.github.andrewk2112.utility.common.utility.Result

/**
 * Describes all possible basic errors can happen in remote data sources.
 *
 * Extends [Exception] to be compatible with [Result].
 *
 * Can be extended further by getting turned into a `sealed class`:
 * in this case it will be possible to check all available failure cases in a comprehensive type-safe manner.
 *
 * Can be wrapped into even more complex sealed classes describing particular error cases
 * with an availability to fallback into these basic ones.
 *
 * Doesn't describe particular localized error messages
 * and doesn't provide any features of the framework layer (from the Clean Architecture).
 *
 * Provides each constructor of the parent [Exception] class as all of them have little differences in minor details.
 */
class RemoteDataSourceException : Exception {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(cause: Throwable?) : super(cause)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
}
