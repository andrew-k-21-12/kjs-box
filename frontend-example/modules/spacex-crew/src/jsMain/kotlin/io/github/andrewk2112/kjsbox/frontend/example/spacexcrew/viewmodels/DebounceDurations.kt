package io.github.andrewk2112.kjsbox.frontend.example.spacexcrew.viewmodels

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * Reusable debounce values - can be extracted into a separate Gradle module to be shared for all view models.
 */
object DebounceDurations {
    val regular: Duration inline get() = 1_000.milliseconds
}
