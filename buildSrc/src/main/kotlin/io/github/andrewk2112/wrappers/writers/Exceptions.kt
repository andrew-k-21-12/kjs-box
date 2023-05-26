package io.github.andrewk2112.wrappers.writers

/**
 * When some source needed for a wrapper has been failed to be written.
 */
internal class SupportingSourceWritingException(cause: Throwable) : Exception(cause)

/**
 * When writing a resource wrapper has failed.
 */
internal class WrapperWritingException(cause: Throwable) : Exception(cause)
