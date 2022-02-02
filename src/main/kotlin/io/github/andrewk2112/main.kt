package io.github.andrewk2112

import io.github.andrewk2112.components.root
import kotlinext.js.require
import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window

/**
 * The app's entry point.
 * */
fun main() {
    window.onload = {
        require("./css/main.css") // putting the dot in front
                                  // to fetch the style and make it processed by webpack correctly
        document.getElementById("root")?.let { rootElement ->
            render(rootElement) {
                child(root)
            }
        }
    }
}
