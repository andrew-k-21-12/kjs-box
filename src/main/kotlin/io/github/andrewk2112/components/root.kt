package io.github.andrewk2112.components

import kotlinext.js.require
import react.*

/**
 * Describes styles for the underlying root container and loads the topmost component.
 * */
val root = fc<Props> {
    require("./css/root.css")
    child(app)
}
