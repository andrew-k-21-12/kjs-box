package io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.utility

import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.RootComponent
import io.github.andrewk2112.kjsbox.frontend.example.dependencyinjection.kodein.KodeinRootComponent
import react.Context
import react.createContext
import react.useContext

/**
 * Gets a scoped instance of [RootComponent].
 */
fun useRootComponent(): RootComponent = useContext(rootComponentContext)

/** Sets and gets scoped [RootComponent] instances. */
private val rootComponentContext: Context<RootComponent> = createContext(KodeinRootComponent())
