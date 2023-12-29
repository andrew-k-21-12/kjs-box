package io.github.andrewk2112.kjsbox.frontend.example.materialdesign.dependencyinjection

import react.createContext

/** Handles entry points for [MaterialDesignComponent]'s injection. */
val materialDesignComponentContext = createContext(MaterialDesignComponent())
