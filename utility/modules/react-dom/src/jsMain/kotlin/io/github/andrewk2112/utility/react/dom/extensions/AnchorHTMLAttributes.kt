package io.github.andrewk2112.utility.react.dom.extensions

import react.dom.html.AnchorHTMLAttributes
import react.dom.html.HTMLAttributes
import web.window.WindowTarget

/**
 * Sets an [AnchorHTMLAttributes.href] to be opened in a new blank window safely:
 * the opened window should not be able to modify its opener with `window.opener.window.location.replace(...)`.
 * This safety concern is only actual when new tabs (windows) are involved.
 *
 * Overrides values of [AnchorHTMLAttributes.target] and [HTMLAttributes.rel],
 * uses an [AnchorHTMLAttributes.href] value as the one returned by getter.
 */
var AnchorHTMLAttributes<*>.safeBlankHref: String?
    get() = href
    set(value) {
        href   = value
        target = WindowTarget._blank
        rel    = "noopener noreferrer"
    }
