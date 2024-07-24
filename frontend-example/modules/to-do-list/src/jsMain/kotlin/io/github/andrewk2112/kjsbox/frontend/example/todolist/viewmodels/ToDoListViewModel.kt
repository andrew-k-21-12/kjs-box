package io.github.andrewk2112.kjsbox.frontend.example.todolist.viewmodels

import io.github.andrewk2112.kjsbox.frontend.example.todolist.data.Task
import io.github.andrewk2112.kjsbox.frontend.example.todolist.data.TasksDataSource
import kotlinx.coroutines.flow.*

/**
 * All UI states and logic for a to-do list.
 */
class ToDoListViewModel(private val tasksDataSource: TasksDataSource) {

    // UI states.

    class UiState(val tasks: List<TaskUiState>)

    class TaskUiState(val task: Task, val onRemove: () -> Unit)



    // Public.

    fun addTask(taskDescription: String) {
        tasksDataSource.createTask(taskDescription)
    }

    val uiState: Flow<UiState> = tasksDataSource.readAllTasks().map { allTasks ->
                                     UiState(
                                         allTasks.map { task ->
                                             TaskUiState(task) { tasksDataSource.deleteTask(task.id) }
                                         }
                                     )
                                 }

}
