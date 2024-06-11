package screens.test

import ClientRepository
import TestAnswers
import TestModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AnswersTestViewModel(
    componentContext: ComponentContext,
    mainCoroutineContext: CoroutineContext,
    private val repository: ClientRepository,
    val test: TestModel,
    val onStudentClick: (testAnswers: TestAnswers) -> Unit,
    val onFinished: () -> Unit,
) : ComponentContext by componentContext {

    private val scope = coroutineScope(mainCoroutineContext + SupervisorJob())

    private val _studentsPassedTest: MutableStateFlow<List<TestAnswers>> = MutableStateFlow(listOf())
    val studentsPassedTest: StateFlow<List<TestAnswers>> = _studentsPassedTest.asStateFlow()

    init {
        scope.launch {
            _studentsPassedTest.update { repository.getAnswers(test.id) }
        }
    }
}
