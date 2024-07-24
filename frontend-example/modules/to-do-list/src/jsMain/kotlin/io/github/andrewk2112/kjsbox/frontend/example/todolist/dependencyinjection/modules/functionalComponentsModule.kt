package io.github.andrewk2112.kjsbox.frontend.example.todolist.dependencyinjection.modules

import io.github.andrewk2112.kjsbox.frontend.example.todolist.components.ToDoList
import io.github.andrewk2112.utility.react.components.FunctionalComponentFactory
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

/** Provides [FunctionalComponentFactory]s. */
val functionalComponentsModule inline get() = DI.Module("FunctionalComponents") {

    // All functional component factories should be singletons
    // to keep their underlying functional components as singletons as well:
    // this allows sticking to the original React structure
    // when a particular functional component is declared only once.
    bindSingleton { ToDoList(instance()) }

}
