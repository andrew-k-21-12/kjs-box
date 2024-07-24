package io.github.andrewk2112.kjsbox.frontend.example.todolist.components

import io.github.andrewk2112.kjsbox.frontend.example.todolist.dependencyinjection.ToDoListComponent
import io.github.andrewk2112.utility.react.components.FC
import react.useMemo

/** Just a trampoline component to set up a dependency injection component and render an entry point. */
val Root by FC {
    useMemo { ToDoListComponent() }.getToDoList().component()
}
