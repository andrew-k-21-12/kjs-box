package io.github.andrewk2112.kjsbox.frontend.example.todolist.dependencyinjection.modules

import io.github.andrewk2112.kjsbox.frontend.example.todolist.viewmodels.ToDoListViewModel
import io.github.andrewk2112.utility.kodein.extensions.bindProvider // this import is the key point here!
import org.kodein.di.DI
import org.kodein.di.instance

/** Provides view models. */
val viewModelsModule inline get() = DI.Module("ViewModels") {

    // View models are usually injected inside functional component factories which are singletons:
    // for this reason it's important to use a dynamic provider mechanism for view models,
    // or multiple React components will share the same view model instance coming from their singleton factory.
    bindProvider { ToDoListViewModel(instance()) }

}
