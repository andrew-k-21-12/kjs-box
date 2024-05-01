package io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.*

internal actual class FileMetadataWriter {

    @OptIn(ExperimentalForeignApi::class)
    actual fun setLastModifiedTime(filePath: String, timeMillis: Long): Boolean =
        NSFileManager.defaultManager.setAttributes(
            mapOf(NSFileModificationDate to NSDate.dateWithUnixTime(timeMillis)),
            filePath,
            null
        )

}
