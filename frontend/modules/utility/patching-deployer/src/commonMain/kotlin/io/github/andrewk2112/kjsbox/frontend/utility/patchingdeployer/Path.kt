package io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer

import okio.Path

/**
 * The same as [Path.relativeTo] but returns `null` on failures instead of throwing exceptions.
 */
internal fun Path.relativeToOrNull(other: Path): Path? =
    try {
        relativeTo(other)
    } catch (_: IllegalArgumentException) {
        null
    }
