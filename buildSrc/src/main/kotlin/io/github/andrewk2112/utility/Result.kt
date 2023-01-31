package io.github.andrewk2112.utility

/**
 * A type-safe wrapper for exception-throwing execution results:
 * helpful for declaring concrete exception types to make them a part of a contract.
 *
 * Should be reorganized into a sealed interface in the future.
 *
 * @param T The type of the successful result's value.
 * @param E The type of the failure's exception.
 */
internal sealed class Result<T, E : Exception> {

    /**
     * A wrapper for a [value] of a successful execution's result.
     *
     * Should be reorganized into a value class in the future.
     */
    class Success<T, E : Exception>(private val value: T) : Result<T, E>() {
        override fun getThrowing(): T = value
    }

    /**
     * A wrapper for an [exception] of a failed execution.
     *
     * Should be reorganized into a value class in the future.
     */
    class Failure<T, E : Exception>(private val exception: E) : Result<T, E>() {
        @Throws(Exception::class)
        override fun getThrowing(): T = throw exception
    }

    /**
     * Retrieves the execution result's value [T] or throws an exception [E] if something went wrong.
     */
    @Throws(Exception::class)
    internal abstract fun getThrowing(): T

}
