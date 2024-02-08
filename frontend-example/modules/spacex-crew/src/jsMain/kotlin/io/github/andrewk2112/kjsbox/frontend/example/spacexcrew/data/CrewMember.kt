package io.github.andrewk2112.kjsbox.frontend.example.spacexcrew.data

import kotlinx.serialization.Serializable

/**
 * It's better to extract an interface providing all data properties of this structure
 * to decouple it from [Serializable] and make it possible to swap various underlying implementations:
 *
 * ```kotlin
 * interface CrewMember {
 *     val id: String
 *     val name: String
 *     val image: String
 * }
 * ```
 *
 * Keeping everything as it is now to simplify the example code.
 */
@Serializable
class CrewMember(val id: String, val name: String, val image: String)
