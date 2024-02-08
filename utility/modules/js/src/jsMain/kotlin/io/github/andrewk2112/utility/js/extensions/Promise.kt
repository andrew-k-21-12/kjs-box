package io.github.andrewk2112.utility.js.extensions

import kotlinx.coroutines.await
import org.w3c.fetch.Response
import kotlin.js.Promise

/**
 * Syntax sugar to await for raw [Response] text.
 *
 * @throws Throwable For any kind of connection error.
 */
suspend fun Promise<Response>.awaitAsText(): String = await().text().await()
