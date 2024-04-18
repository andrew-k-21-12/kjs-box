package io.github.andrewk2112.kjsbox.backend.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.conditionalheaders.*
import io.ktor.server.routing.*

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

// TODO: Replace direct path to React bundle with some argument or config.

fun main() {
    embeddedServer(Netty) {
        install(Compression) {
            gzip()
        }
        install(ConditionalHeaders) // adds proper "Last-Modified" headers according to states of served files
        routing {
            singlePageApplication {
                react("build/dist/js/productionExecutable")
            }
        }
    }.start(wait = true)
}
