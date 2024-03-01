package io.github.andrewk2112.utility.js.extensions

import org.w3c.dom.Window

/**
 * Opens a [url] in a new blank window safely:
 * the opened window should not be able to modify its opener with `window.opener.window.location.replace(...)`.
 * This safety concern is only actual when new tabs (windows) are involved.
 */
fun Window.openBlankSafely(url: String): Window? = open(url, "_blank", "noopener,noreferrer")?.also { it.opener = null }
