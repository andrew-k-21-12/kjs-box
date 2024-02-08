package io.github.andrewk2112.kjsbox.frontend.example.spacexcrew.viewmodels

import io.github.andrewk2112.kjsbox.frontend.example.spacexcrew.data.CrewMember
import io.github.andrewk2112.kjsbox.frontend.example.spacexcrew.data.CrewRemoteDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * This is a state holder to provide SpaceX crew searching UI functionality.
 *
 * It's better to keep view models in a separate package (possibly even in a separate Gradle module)
 * and make sure they are fully decoupled from any UI, framework or other kinds of implementation details.
 */
@OptIn(FlowPreview::class)
class RootViewModel {

    // Public.

    /**
     * Everything should be represented in UI.
     */
    data class UiState(val foundCrewMembers: List<CrewMember>)

    /**
     * Use this method to push name queries to search [CrewMember]s for.
     */
    fun submitNameSearchQuery(nameSearchQuery: String) {
        this.nameSearchQuery.value = nameSearchQuery
    }

    /**
     * Use this method to notify the view model to be disposed.
     */
    fun onCleared() {
        coroutineScope.cancel()
    }

    /** Actual UI state to be observed and processed by UI-rendering layers. */
    val uiState: StateFlow<UiState> by ::_uiState



    // Private.

    /** Default dispatcher to launch all coroutines inside the view model, in the ideal case should be injected. */
    private val coroutineDispatcher: CoroutineDispatcher inline get() = Dispatchers.Main.immediate

    /**
     * Default scope to launch all coroutines, can be provided in the constructor if needed for easier testing.
     * There is no big deal in using of [SupervisorJob],
     * it just allows to finish all coroutines have been already started,
     * but it won't suppress their uncaught exceptions.
     */
    private val coroutineScope = CoroutineScope(SupervisorJob() + coroutineDispatcher)

    /** Just a mean to access required data, should be injected. */
    private val crewRemoteDataSource = CrewRemoteDataSource()

    private val _uiState = MutableStateFlow(UiState(emptyList()))
    private val nameSearchQuery = MutableStateFlow<String?>(null)
    init {
        // While the view model is active, performing each new search query by the remote data source with some debounce
        // and providing responses without any error processing to the UI state.
        coroutineScope.launch {
            nameSearchQuery
                .filterNotNull()
                .debounce(DebounceDurations.regular)
                .collectLatest { nameSearchQuery ->
                    crewRemoteDataSource.getByName(nameSearchQuery).getOrNull()?.value?.let { retrievedCrewMembers ->
                        _uiState.update { it.copy(foundCrewMembers = retrievedCrewMembers) }
                    }
                }
        }
    }

}
