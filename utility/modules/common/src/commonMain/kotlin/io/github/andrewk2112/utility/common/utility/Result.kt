package io.github.andrewk2112.utility.common.utility

import kotlin.jvm.JvmInline

/**
 * A type-safe wrapper for results of exception-throwing operations.
 *
 * Helpful for declaring a concrete exception type to be a part of function's (lambda's) definition -
 * to follow Kotlin's design to avoid exceptions ([Throws]) as a way to return full-blown typed values from functions.
 *
 * @param T The type of successful result's value.
 * @param E The type of failure's exception.
 */
sealed interface Result<T, E : Throwable> {

    // Common API.

    /**
     * Returns a result value in the case of [Success] or `null` - if some exception has occurred.
     *
     * Wrapping the result value into [Success] helps to make this method work even for nullable result values.
     */
    fun getOrNull(): Success<T, E>?

    /**
     * Returns an execution's result value [T] or throws an exception [E] if something went wrong.
     *
     * **Using this method is discouraged** to a favor of type-safe alternatives,
     * use it only when there is no concern on how to process (catch) exceptions at all.
     */
    @Throws(Throwable::class)
    fun getOrThrow(): T

    /**
     * Just throws an exception [E] in the case when the execution has failed.
     *
     * **Using this method is discouraged** to a favor of type-safe alternatives,
     * use it only when there is no concern on how to process (catch) exceptions at all.
     */
    @Throws(Throwable::class)
    fun throwOnFailure()

    /**
     * Retrieves an execution result's [Failure] or `null` if everything was successful.
     */
    fun getFailureOrNull(): Failure<T, E>?

    /**
     * Retrieves an execution result's exception [E] or `null` if everything was successful.
     */
    fun getExceptionOrNull(): E?



    // Implementations.

    /**
     * A wrapper for a successful execution's result.
     */
    @JvmInline
    value class Success<T, E : Throwable>(val value: T) : Result<T, E> {
        override fun getOrNull(): Success<T, E> = this
        override fun getOrThrow(): T = value
        override fun throwOnFailure() = Unit
        override fun getFailureOrNull(): Failure<T, E>? = null
        override fun getExceptionOrNull(): E? = null
    }

    /**
     * A wrapper for a failed execution's result.
     */
    @JvmInline
    value class Failure<T, E : Throwable>(val exception: E) : Result<T, E> {
        override fun getOrNull(): Success<T, E>? = null
        @Throws(Throwable::class)
        override fun getOrThrow(): T = throw exception
        @Throws(Throwable::class)
        override fun throwOnFailure() = throw exception
        override fun getFailureOrNull(): Failure<T, E> = this
        override fun getExceptionOrNull(): E = exception
    }



    // Additional utility: see `kotlin.Result` for more.

    companion object {

        /**
         * Allows to provide an alternative result value with the [onFailure] lambda in the case of failed result
         * or just helps to do an early return in this case.
         *
         * The only reason for this function to be outside the [Result] interface's protocol -
         * is to make it `inline`, so all variants of `return`s are available inside the [onFailure] at the call site.
         */
        inline fun <T, E : Throwable> Result<T, E>.getOrElse(onFailure: (E) -> T): T =
            when (this) {
                is Success -> value
                is Failure -> onFailure(exception)
            }

        /**
         * Runs a [block] catching exceptions only of the type [E] and wrapping them into a [Result].
         *
         * Helpful to convert exception-throwing functions into [Result]-returning ones.
         *
         * Even for Java it's better to follow the rule that exceptions are only for exceptional cases,
         * but for Kotlin it becomes much more important:
         * errors from exceptions start mixing with coroutines' `CancellationException`s,
         * exceptions can't be generic and are not forced to be caught
         * (so updating lists of exceptions will cause crashes or incorrect behavior),
         * there is no way to declare exceptions in a clean way for lambdas.
         * Taking into account some of the points above, it's better to avoid exceptions in Kotlin as much as possible:
         * this function is mostly for this purpose - to bridge legacy (mostly Java) code with exceptions to Kotlin.
         *
         * @throws Throwable While in general it shouldn't throw any exceptions,
         *                   it still can happen if a wrong type of target exception [E] was specified
         *                   (which doesn't cover all actual exceptions being thrown inside the [block]).
         */
        @Throws(Throwable::class)
        inline fun <T, reified E : Throwable> runCatchingTypedException(block: () -> T): Result<T, E> =
            try {
                Success(block())
            } catch (throwable: Throwable) {
                if (throwable is E) {
                    Failure(throwable)
                } else {
                    throw throwable
                }
            }

    }

}
