package io.github.andrewk2112.kjsbox.frontend.example.spacexcrew.data

import kotlinx.serialization.Serializable

/**
 * A [List] of typed [docs], should be extended with pagination metadata when will be needed.
 *
 * An interface should be extracted to decouple from a particular serialization engine.
 * Keeping it as is to avoid complication of the example.
 *
 * Note that interfaces and abstract classes with generics
 * are not supported for inplace (having no particular declared classes) serialization by [Serializable].
 *
 * @param docs The [List] of records of any kinds, generic arrays are not suitable as they don't support dynamic typing.
 */
@Serializable
class PaginatedDocuments<T>(val docs: List<T>)
