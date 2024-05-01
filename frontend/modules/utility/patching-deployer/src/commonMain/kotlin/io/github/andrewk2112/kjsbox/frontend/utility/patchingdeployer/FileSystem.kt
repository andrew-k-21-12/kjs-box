package io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer

import okio.FileMetadata
import okio.FileSystem
import okio.Path

/**
 * The same as [FileSystem.metadataOrNull] but suppresses all thrown exceptions (returns `null` in their case).
 */
internal fun FileSystem.metadataOrNullWithoutThrows(path: Path): FileMetadata? =
    try {
        metadataOrNull(path)
    } catch (_: Throwable) {
        null
    }
