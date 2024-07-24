package io.github.andrewk2112.kjsbox.frontend.example.todolist.dependencyinjection

import io.github.andrewk2112.kjsbox.frontend.example.todolist.components.ToDoList
import io.github.andrewk2112.kjsbox.frontend.example.todolist.dependencyinjection.modules.dataModule
import io.github.andrewk2112.kjsbox.frontend.example.todolist.dependencyinjection.modules.functionalComponentsModule
import io.github.andrewk2112.kjsbox.frontend.example.todolist.dependencyinjection.modules.viewModelsModule
import io.github.andrewk2112.utility.kodein.KodeinDirectInjection

/**
 * Stores and provides all dependencies for the To-Do List example.
 */
class ToDoListComponent {

    /**
     * In this particular example this getter becomes a kind of entry point for all further injections.
     */
    fun getToDoList(): ToDoList = kodeinInjection()

    private val kodeinInjection = KodeinDirectInjection(dataModule, functionalComponentsModule, viewModelsModule)

}
