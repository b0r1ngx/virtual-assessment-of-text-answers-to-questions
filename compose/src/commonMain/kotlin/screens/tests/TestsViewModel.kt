package screens.tests

import Test
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import model.UiState
import viewmodel.AppViewModel

class TestsViewModel(
//    repository:
): AppViewModel {
    private val _uiState = MutableStateFlow(UiState())
    override val uiState = _uiState.asStateFlow()

    val _tests = mutableListOf<Test>()
    val tests = listOf<Test>()

    private var fetchJob: Job? = null

    fun getTests() {
        fetchJob?.cancel()
        fetchJob
    }
}