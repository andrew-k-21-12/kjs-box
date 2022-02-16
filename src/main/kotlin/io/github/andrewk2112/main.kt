package io.github.andrewk2112

import io.github.andrewk2112.containers.app
import io.github.andrewk2112.dinjection.di
import io.github.andrewk2112.redux.StoreFactory
import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import org.kodein.di.instance
import react.redux.provider
import styled.injectGlobal

/**
 * The app's entry point.
 * */
fun main() {
    window.onload = {
        injectGlobal(CLEARFIX_AND_ROOT_CSS) // using injection instead of static files to get minification
        render(document.getElementById("root")!!) {
            val storeFactory by di.instance<StoreFactory>()
            provider(storeFactory.create()) { // setting the global store for the app
                app()
            }
        }
    }
}

/** Basic CSS to be applied to prepare further layouts. */
private val CLEARFIX_AND_ROOT_CSS = """
    /* Basic styles to be applied to all elements (including ones outside React components). */
    * {
        box-sizing: border-box; /* width and height of boxes include borders, margins and padding */
        margin:  0;
        padding: 0;
        outline: 0;
    }
    /* Styles for the root element to inject contents into. */
    #root {
        height: 100vh;    /* fits the entire height of a screen
                             (blocks take the entire width by default
                             and are not stretched horizontally by internal elements) */
        overflow: scroll; /* if the contents are too tall, they will start to scroll inside
                             (both vertically and horizontally) */
    }
""".trimIndent()
