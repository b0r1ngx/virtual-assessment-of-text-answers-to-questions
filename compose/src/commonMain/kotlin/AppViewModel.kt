import kotlinx.coroutines.flow.StateFlow
import model.UiState

interface AppViewModel {
    val uiState: StateFlow<UiState>
}