package io.github.andrewk2112.utility

import kotlinx.browser.window
import react.dom.html.AnchorHTMLAttributes
import web.window.WindowTarget

/**
 * Opens the requested [url] safely in a new blank window.
 *
 * The opened window will have no access to the opener, so it should be:
 * - safer, as some browsers (e.g. Chrome) can let the opened window modify its opener with
 *   `window.opener.window.location.replace(...)`
 * - more performant,
 *   as there is an opinion that new tabs with a reference to the opener are executed on the same process
 *
 * By using one wrapper method for all external navigation actions it's possible to extend it (e.g. add logging) easier.
 */
fun openBlankWindowSafely(url: String) {
    window.open(url, "_blank", "noopener,noreferrer")?.also { it.opener = null }
}

/**
 * Sets the destination endpoint to be opened in a new blank window for an anchor element safely.
 *
 * Read the docs for [openBlankWindowSafely] to get additional explanations.
 */
var AnchorHTMLAttributes<*>.safeBlankHref: String?
    get() = href
    set(value) {
        href   = value
        target = WindowTarget._blank
        rel    = "noopener noreferrer"
    }
