package io.github.andrewk2112.kjsbox.frontend.example.todolist.components

import io.github.andrewk2112.kjsbox.frontend.example.todolist.viewmodels.ToDoListViewModel
import io.github.andrewk2112.utility.common.utility.Provider
import io.github.andrewk2112.utility.coroutines.react.extensions.asReactState
import io.github.andrewk2112.utility.react.components.FunctionalComponentFactory
import react.ChildrenBuilder
import react.Props
import react.dom.events.MouseEventHandler
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import react.FC
import react.useCallback
import react.useRef
import react.useState
import web.html.HTMLButtonElement
import web.html.HTMLInputElement
import web.html.InputType

/**
 * This is an example of constructor injection for renderable React components ([FC]s):
 * in this case there is no need to get required dependencies manually from dependency injection components.
 */
class ToDoList(private val viewModel: Provider<ToDoListViewModel>) : FunctionalComponentFactory<Props>() {

    override fun ChildrenBuilder.render(props: Props) {

        // Injecting and keeping only one view model instance for each corresponding functional component instance.
        val viewModel by useState { viewModel.get() }
        val uiState   by viewModel.uiState.asReactState()

        val inputRef = useRef<HTMLInputElement>(null)
        val onAddTask: MouseEventHandler<HTMLButtonElement> = useCallback {
            val taskDescription     = inputRef.current?.value ?: return@useCallback
            inputRef.current?.value = ""
            viewModel.addTask(taskDescription)
        }

        input {
            ref  = inputRef
            type = InputType.text
        }

        button {
            onClick = onAddTask
            +"+"
        }

        ul {
            for (taskUiState in uiState?.tasks ?: return@ul) {
                li {
                    key = taskUiState.task.id
                    +taskUiState.task.description
                    button {
                        onClick = taskUiState.onRemove.unsafeCast<MouseEventHandler<HTMLButtonElement>>()
                        +"-"
                    }
                }
            }
        }

    }

}
