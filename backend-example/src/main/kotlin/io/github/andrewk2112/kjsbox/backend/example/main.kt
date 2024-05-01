package io.github.andrewk2112.kjsbox.backend.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.conditionalheaders.*
import io.ktor.server.routing.*
import java.io.File

fun main(args: Array<String>) {
    embeddedServer(Netty) {
        install(Compression) {
            gzip()
        }
        install(ConditionalHeaders) // adds proper "Last-Modified" headers according to states of served files
        routing {
            singlePageApplication {
                react(prepareSpaPath(this@embeddedServer, args))
            }
        }
    }.start(wait = true)
}

/**
 * Extracts an SPA path from the [mainArguments] or returns the [FALLBACK_SPA_PATH].
 *
 * @throws IllegalStateException If no SPA folder could be located.
 */
@Throws(IllegalStateException::class)
private fun prepareSpaPath(application: Application, mainArguments: Array<String>): String {
    val spaPath = mainArguments.firstOrNull()
        ?: FALLBACK_SPA_PATH.also { application.log.info("No SPA path was provided, trying to use the fallback one.") }
    return spaPath.takeIf { File(it).isDirectory }
        ?: throw IllegalStateException("Could not locate the SPA folder")
}

private const val FALLBACK_SPA_PATH = "build/dist/js/productionExecutable"
