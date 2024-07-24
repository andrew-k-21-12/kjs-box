package io.github.andrewk2112.kjsbox.frontend.example.todolist.dependencyinjection.modules

import io.github.andrewk2112.kjsbox.frontend.example.todolist.data.TasksDataSource
import org.kodein.di.DI
import org.kodein.di.bindSingleton

/** Provides data sources. */
val dataModule inline get() = DI.Module("Data") {

    bindSingleton { TasksDataSource() }

}
