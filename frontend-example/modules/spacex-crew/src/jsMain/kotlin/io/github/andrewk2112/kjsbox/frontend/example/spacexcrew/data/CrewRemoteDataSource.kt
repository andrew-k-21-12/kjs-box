package io.github.andrewk2112.kjsbox.frontend.example.spacexcrew.data

import io.github.andrewk2112.utility.common.utility.Result
import io.github.andrewk2112.utility.js.extensions.awaitAsText
import io.github.andrewk2112.utility.js.extensions.minify
import kotlinx.browser.window
import kotlinx.serialization.json.Json
import org.w3c.fetch.RequestInit
import kotlin.js.json



// Public.

/**
 * Remote data source to interact with SpaceX crew records.
 *
 * In the ideal case should have an abstraction layer extracted to substitute various implementations seamlessly:
 *
 * ```kotlin
 * interface CrewRemoteDataSource {
 *     suspend fun getByName(nameQuery: String): Result<List<CrewMember>, RemoteDataSourceException>
 * }
 * ```
 *
 * Keeping it with the built-in implementation to simplify the overall example.
 */
class CrewRemoteDataSource {

    // Public.

    /**
     * Retrieves all [CrewMember]s who have any occurrence of the [nameQuery] in their name.
     */
    suspend fun getByName(nameQuery: String): Result<List<CrewMember>, RemoteDataSourceException> =
        exceptionsCatcher {
            val rawResponse = window
                .fetch(
                    "${spaceXDataApi.v4}/crew/query",
                    RequestInit(
                        method  = "POST", // using direct strings here and for headers, as there are no other usages yet
                        headers = json("Content-Type" to "application/json"),
                        body    = createCrewFetchingRequestBodyJson(nameQuery)
                    )
                )
                .awaitAsText()
            // One day possibly there will be better sequential (stream-based) parsing,
            // but for now there are no trivial ways to do that yet, so consuming of the entire string is used, see:
            // https://github.com/Kotlin/kotlinx.serialization/issues/2553
            json.decodeFromString<PaginatedDocuments<CrewMember>>(rawResponse).docs
        }



    // Private.

    /** The host to perform requests to. Possibly can be injected or configured dynamically in the future. */
    private val spaceXDataHost = "https://api.spacexdata.com"

    /** Different available API versions. */
    private val spaceXDataApi = object {
        val v4 = "$spaceXDataHost/v4"
    }

    /** [Json] serialization configurations. Possibly can be injected in the future. */
    private val json = Json { ignoreUnknownKeys = true }

    /** Provides mechanisms to handle basic exceptions - can be injected, combined with other middlewares. */
    private val exceptionsCatcher = RemoteDataSourceExceptionsCatcher()

}



// Utility.

/**
 * Having this function here is discouraged as it violates single responsibility:
 * it's better to be extracted and generalized.
 * But for now, while there are no other similar usages, it's possible to keep it as is for brevity.
 */
private fun createCrewFetchingRequestBodyJson(nameQuery: String): String =
    JSON.minify("""
        {
            "query": {
                "name": {
                    "${"$"}regex":   "$nameQuery",
                    "${"$"}options": "i"
                }
            },
            "options": {
                "pagination": false
            }
        }
    """)
