package io.github.andrewk2112

import browser.document
import kotlinx.css.*
import react.*
import react.dom.client.createRoot
import styled.injectGlobal

/** The app's entry point. */
@EagerInitialization
@OptIn(ExperimentalStdlibApi::class)
@Suppress("unused", "DEPRECATION")
private val main = run {
    injectGlobal(createClearfixCss()) // injecting global styles,
                                      // using code instead of static files to get minification
    createRoot(document.getElementById(reactRootElementID)!!) // looking for the root element,
        .render(app.create())                                 // starting React configuration and rendering inside
}

/**
 * Creates basic styles to be applied to all elements (including ones outside React components).
 */
private fun createClearfixCss() = CssBuilder(allowClasses = false).apply {
    rule("*") {
        boxSizing = BoxSizing.borderBox // width and height of boxes include borders, margins and padding
        margin(0.px)
        padding(0.px)
    }
}

/** An ID of the root element to render React-based contents inside. */
private val reactRootElementID get() = "root"
