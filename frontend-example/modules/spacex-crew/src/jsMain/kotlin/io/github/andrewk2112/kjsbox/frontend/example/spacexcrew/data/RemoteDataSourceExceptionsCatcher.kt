package io.github.andrewk2112.kjsbox.frontend.example.spacexcrew.data

import io.github.andrewk2112.utility.common.utility.Result
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.cancellation.CancellationException

/**
 * Provides middleware to catch all possible exceptions and wrap (convert) them into typed [Result.Failure]s.
 *
 * One of possible alternative ways - is to use some [CoroutineExceptionHandler],
 * but it's limited only to `suspend` contexts and doesn't support return values.
 *
 * It's better to have an abstraction extracted:
 *
 * ```kotlin
 * interface ExceptionsCatcher<E : Throwable> {
 *     suspend operator fun <T> invoke(block: suspend () -> T): Result<T, E>
 * }
 * ```
 *
 * Keeping it with the built-in implementation to simplify the overall example.
 */
class RemoteDataSourceExceptionsCatcher {

    /**
     * The exceptions-catching middleware itself.
     *
     * In the ideal case it should not rethrow any exceptions to guarantee single source of errors to be processed,
     * but for some safe cases - such as, for example [CancellationException]s - it's ok to throw exceptions further.
     */
    suspend operator fun <T> invoke(block: suspend () -> T): Result<T, RemoteDataSourceException> =
        // Unfortunately Kotlin/JS uses `Throwable` (`JsError`) as the base one for all JS exceptions
        // (so it's impossible to differentiate between JS-related and other errors).
        try {
            Result.Success(block())
        } catch (throwable: Throwable) {
            if (throwable is CancellationException) throw throwable // preserving cancellations from coroutines
            Result.Failure(RemoteDataSourceException(throwable))
        }

}
