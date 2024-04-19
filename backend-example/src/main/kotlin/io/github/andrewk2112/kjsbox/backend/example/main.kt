package io.github.andrewk2112.kjsbox.backend.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.conditionalheaders.*
import io.ktor.server.routing.*
import java.io.File

// TODO (FORCED): The only stable but requiring to re-download everything on update way:
//  - all files are configured to never expire;
//  - index.html is configured with ETag describing its update date
//  - each new version has its own version-named folder, e.g. `static/v1`, crash if the version folder already exists;
//  - on each update it's just needed to copy a new version folder, then rapidly swap this tiny entry-point `index.html`.

// TODO (GRADUAL): One more way to have even better optimization:
//  - add hashes for everything - including fonts and images;
//  - keep the gitignored `dist` folder, one day it can become reasonable to check it in;
//  - on each release try to copy all `static` contents to the latest deploy version folder:
//    if some name collision is encountered then either skip a file if its latest update date is equal
//    or throw an exception that a full deployment (described above) is needed;
//  - swap an entry-point `index.html` rapidly - only if the previous step was not aborted.

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
