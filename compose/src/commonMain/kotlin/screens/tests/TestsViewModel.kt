package screens.tests

import AppViewModel
import ClientRepository
import TestModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.UiState
import kotlin.coroutines.CoroutineContext

class TestsViewModel(
    componentContext: ComponentContext,
    mainCoroutineContext: CoroutineContext,
    private val repository: ClientRepository,
    val onCreateTestClick: () -> Unit,
    val onTestClick: (test: TestModel) -> Unit
) : AppViewModel, ComponentContext by componentContext {

    private val scope = coroutineScope(mainCoroutineContext + SupervisorJob())

    private val _uiState = MutableStateFlow(UiState())
    override val uiState = _uiState.asStateFlow()

    // left open the problem of collecting state not depends on lifecycle,
    // what this approach differs of using Decompose Value
    private val _tests: MutableStateFlow<List<TestModel>> = MutableStateFlow(listOf())
    val tests: StateFlow<List<TestModel>> = _tests.asStateFlow()

    init {
        scope.launch {
            _tests.update { repository.getTests() } // mockTests
        }
    }

}
