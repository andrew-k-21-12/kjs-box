package io.github.andrewk2112

import io.github.andrewk2112.dinjection.di
import io.github.andrewk2112.redux.StoreFactory
import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.css.*
import org.kodein.di.direct
import org.kodein.di.instance
import react.Suspense
import react.create
import react.redux.provider
import react.router.dom.BrowserRouter
import styled.injectGlobal

/**
 * The app's entry point.
 * */
fun main() {
    window.onload = {

        // Injecting global styles, using code instead of static files to get minification.
        injectGlobal(createClearfixAndRootCss())

        // Preparing the global state and its processing reducers.
        val store = di.direct.instance<StoreFactory>().create()

        // Looking for the root element, starting React configuration and rendering inside.
        render(document.getElementById(reactRootElementID)!!) {

            // Setting the global store and reducers for the app.
            provider(store) {

                // Enabling routing features.
                BrowserRouter {

                    // Configuring the app with its loading placeholder.
                    Suspense {
                        attrs.fallback = appLoadingPlaceholder.create()
                        app()
                    }

                }

            }

        }

    }
}

/**
 * Creates basic CSS to be applied to prepare further layouts.
 * */
private fun createClearfixAndRootCss() = CssBuilder(allowClasses = false).apply {
    // Basic styles to be applied to all elements (including ones outside React components).
    rule("*") {
        boxSizing = BoxSizing.borderBox // width and height of boxes include borders, margins and padding
        margin(0.px)
        padding(0.px)
    }
    // Styles for the root element to inject contents into.
    rule("#$reactRootElementID") {
        height = 100.vh // fits the entire height of a screen (blocks take the entire width by default
                        // and are not stretched horizontally by internal elements)
        overflow = Overflow.scroll // if the contents are too tall, they will start to scroll inside
                                   // (both vertically and horizontally)
    }
}

/** An ID of the root element to render React-based contents inside. */
private val reactRootElementID get() = "root"
