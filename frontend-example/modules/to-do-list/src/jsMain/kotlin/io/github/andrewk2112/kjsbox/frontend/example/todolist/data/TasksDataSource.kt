package io.github.andrewk2112.kjsbox.frontend.example.todolist.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * An example of data storage class to manage [Task]s.
 *
 * Works in memory.
 *
 * Doesn't have any multithreading support for simplicity of example.
 */
class TasksDataSource {

    // Public.

    fun createTask(taskDescription: String) {
        val newTask = Task((++taskIdCounter).toString(), taskDescription)
        tasks.update {
            buildList {
                add(newTask)
                addAll(it)
            }
        }
    }

    fun readAllTasks(): Flow<List<Task>> = tasks

    fun deleteTask(taskId: String) {
        tasks.update {
            it.filter { task -> task.id != taskId }
        }
    }



    // Private.

    private var taskIdCounter: Long = 0

    private val tasks = MutableStateFlow(emptyList<Task>())

}
