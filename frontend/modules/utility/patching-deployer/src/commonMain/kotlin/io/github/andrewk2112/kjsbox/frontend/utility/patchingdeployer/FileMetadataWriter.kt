package io.github.andrewk2112.kjsbox.frontend.utility.patchingdeployer

/**
 * Provides means to set various file metadata.
 */
internal expect class FileMetadataWriter() {

    /**
     * Sets last modified [timeMillis] for a file at [filePath] and returns `true` in the case of successful completion.
     */
    fun setLastModifiedTime(filePath: String, timeMillis: Long): Boolean

}
