package io.github.andrewk2112.resources

/**
 * When the target paths have failed to be prepared.
 */
internal class PathsPreparationException(cause: Throwable) : Exception(cause)

/**
 * When some error while walking the files has occurred.
 */
internal class FilesWalkingException(cause: Throwable) : Exception(cause)

/**
 * When visiting a particular resource has failed.
 */
internal class ResourceVisitingException(cause: Throwable) : Exception(cause)
