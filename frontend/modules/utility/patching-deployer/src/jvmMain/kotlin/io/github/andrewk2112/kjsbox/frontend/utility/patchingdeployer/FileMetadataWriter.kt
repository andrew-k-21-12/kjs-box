package io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.attribute.FileTime

internal actual class FileMetadataWriter {

    actual fun setLastModifiedTime(filePath: String, timeMillis: Long): Boolean =
        try {
            Files.setLastModifiedTime(Paths.get(filePath), FileTime.fromMillis(timeMillis))
            true
        } catch (_: Exception) {
            false
        }

}
