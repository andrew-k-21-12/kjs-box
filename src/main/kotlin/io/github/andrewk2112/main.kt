package io.github.andrewk2112

import io.github.andrewk2112.dinjection.di
import io.github.andrewk2112.redux.StoreFactory
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.css.*
import org.kodein.di.direct
import org.kodein.di.instance
import react.*
import react.dom.client.createRoot
import react.redux.provider
import react.router.dom.BrowserRouter
import styled.injectGlobal

/**
 * The app's entry point.
 * */
fun main() {
    window.onload = {
        injectGlobal(createClearfixCss()) // injecting global styles,
                                          // using code instead of static files to get minification
        createRoot(document.getElementById(reactRootElementID)!!) // looking for the root element,
            .render(createRootElement())                          // starting React configuration and rendering inside
    }
}

/**
 * Creates basic styles to be applied to all elements (including ones outside React components).
 * */
private fun createClearfixCss() = CssBuilder(allowClasses = false).apply {
    rule("*") {
        boxSizing = BoxSizing.borderBox // width and height of boxes include borders, margins and padding
        margin(0.px)
        padding(0.px)
    }
}

/**
 * Creates a fully configured root [ReactElement] for the app.
 * */
private fun createRootElement(): ReactElement<Props> = fc<Props> {
    provider(di.direct.instance<StoreFactory>().create()) { // setting the global app state and its processing reducers,
        BrowserRouter {                                     // enabling routing features,
            Suspense {                                      // configuring the app with its loading placeholder
                attrs.fallback = appLoadingPlaceholder.create()
                app()
            }
        }
    }
}.create()

/** An ID of the root element to render React-based contents inside. */
private val reactRootElementID get() = "root"
