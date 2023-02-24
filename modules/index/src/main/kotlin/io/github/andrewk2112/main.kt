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

    // Injecting global styles, using the code instead of static files to get minification.
    injectGlobal(clearfixCss)

    // Looking for the target root element, starting React configuration and rendering inside.
    createRoot(document.getElementById(reactRootElementId)!!)
        .render(app.create())

}

/** Basic styles to be applied to all elements (including the ones outside React components). */
private inline val clearfixCss: CssBuilder
    get() = CssBuilder(allowClasses = false).apply {
        rule("*") {
            boxSizing = BoxSizing.borderBox // width and height of boxes include borders, margins and padding
            margin(0.px)
            padding(0.px)
        }
    }

/** An ID of the root element to render React-based contents inside. */
private inline val reactRootElementId get() = "root"
